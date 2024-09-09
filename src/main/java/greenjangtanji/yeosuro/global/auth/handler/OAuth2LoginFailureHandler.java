package greenjangtanji.yeosuro.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");

        String errorMessage;

        if (exception instanceof DisabledException) {
            errorMessage = "탈퇴한 회원입니다.";
        } else if (exception instanceof AuthenticationServiceException) {
            errorMessage = "소셜 로그인 서비스에서 문제가 발생했습니다.";
        } else if (exception instanceof OAuth2AuthenticationException) {
            // OAuth2AuthenticationException의 상세 원인을 가져와서 출력
            Throwable cause = exception.getCause();
            errorMessage = cause != null ? cause.getMessage() : "OAuth2 인증 중 오류가 발생했습니다.";
        } else {
            errorMessage = "소셜 로그인 실패! 서버 로그를 확인해주세요.";
        }

        response.getWriter().write("{\"error\":\"" + errorMessage + "\"}");
        log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", errorMessage);
        log.error("AuthenticationException Type: {}", exception.getClass().getSimpleName());

        // 추가로 상세한 예외 원인 로그
        if (exception.getCause() != null) {
            log.error("Exception Cause: ", exception.getCause());
        }
    }
}

