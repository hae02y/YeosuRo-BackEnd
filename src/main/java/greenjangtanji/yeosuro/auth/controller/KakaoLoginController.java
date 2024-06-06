package greenjangtanji.yeosuro.auth.controller;

import greenjangtanji.yeosuro.auth.dto.KakaoUserInfoResponseDto;
import greenjangtanji.yeosuro.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String client_id;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect_uri;


    @GetMapping("/page")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
        model.addAttribute("location", location);
        return "login";
    }

    //토큰 발급받을 Redirect URL
    @GetMapping("/kakao_auth")
    public ResponseEntity<?> callback (@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping("/kakao_auth")
//    public @ResponseBody String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수, 쿼리스트링에 있는 code값을 받을 수 있음
//        return "카카오 인증 완료" + code;
//    }

}
