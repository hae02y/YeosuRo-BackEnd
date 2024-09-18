package greenjangtanji.yeosuro.global.auth.handler;

import greenjangtanji.yeosuro.global.auth.CustomOAuth2User;
import greenjangtanji.yeosuro.global.jwt.service.JwtService;
import greenjangtanji.yeosuro.user.entity.Role;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            User findUser = userRepository.findByEmail(oAuth2User.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if (oAuth2User.getRole() == Role.GUEST) {
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                String refreshToken = jwtService.createRefreshToken();

                // 쿠키 설정
                Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
                accessTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근할 수 없도록 설정
                accessTokenCookie.setSecure(false); // HTTPS 연결에서만 사용
                accessTokenCookie.setPath("/"); // 애플리케이션 전체에서 쿠키 접근 가능
                accessTokenCookie.setMaxAge(3600); // 쿠키 만료 시간 설정 (초 단위)

                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(false);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setMaxAge(3600);

                response.addCookie(accessTokenCookie);
                response.addCookie(refreshTokenCookie);

                // 사용자 ID를 포함한 JSON 응답
                response.setContentType("application/json"); // 응답 형식 설정
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = String.format("{\"userId\": %d}", findUser.getId()); // 사용자 ID를 JSON 형식으로 생성
                response.getWriter().write(jsonResponse); // JSON 응답 작성

                // 회원가입 페이지로 리다이렉트
                String redirectUrl = "http://localhost:3000/login/oauth";
                response.sendRedirect(redirectUrl); // 프론트엔드의 회원가입 페이지로 리다이렉트


            } else {
                // 로그인 성공 시 처리
                loginSuccess(response, oAuth2User);

                // 사용자 ID를 포함한 JSON 응답
                response.setContentType("application/json"); // 응답 형식 설정
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = String.format("{\"userId\": %d}", findUser.getId()); // 사용자 ID를 JSON 형식으로 생성
                response.getWriter().write(jsonResponse); // JSON 응답 작성

                String redirectUrl = "http://localhost:3000/login/oauth/callback";
                response.sendRedirect(redirectUrl);

            }
        } catch (Exception e) {
            log.error("Authentication Success Handler Error: ", e);
            throw e;
        }
    }


    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        // 쿠키 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true); // 자바스크립트에서 접근할 수 없도록 설정
        accessTokenCookie.setSecure(false); // HTTPS 연결에서만 사용
        accessTokenCookie.setPath("/"); // 애플리케이션 전체에서 쿠키 접근 가능
        accessTokenCookie.setMaxAge(3600); // 쿠키 만료 시간 설정 (초 단위)

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(3600);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

    }
}


