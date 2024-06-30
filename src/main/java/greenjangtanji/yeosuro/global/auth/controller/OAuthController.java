package greenjangtanji.yeosuro.global.auth.controller;

import greenjangtanji.yeosuro.global.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OAuthController {

    private final CustomOAuth2UserService oAuth2UserService;


    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "redirect:/"; // 로그인 성공 후 리다이렉트할 경로 지정 (예: 메인 페이지 등)
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "redirect:/login?error"; // 로그인 실패 후 리다이렉트할 경로 지정 (로그인 폼에 에러 메시지 표시 가능)
    }
}
