package greenjangtanji.yeosuro.global.mail.controller;

import greenjangtanji.yeosuro.global.handler.ApiResponse;
import greenjangtanji.yeosuro.global.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
@Validated
public class MailController {
    private final MailService mailService;

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestParam String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            String trimmedMail = mail.trim();
            int number = mailService.sendMail(trimmedMail);
            map.put("success", Boolean.TRUE);
            map.put("number", number);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    @GetMapping("/mailCheck")
    public ResponseEntity<ApiResponse<Void>> mailCheck(@RequestParam String code) {
        try {
            int codeInt = Integer.parseInt(code);
            boolean isMatch = mailService.isCodeValid(codeInt);

            if (isMatch) {
                ApiResponse<Void> response = new ApiResponse<>(HttpStatus.OK.value(), "인증이 완료되었습니다.", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "인증 코드가 유효하지 않거나 시간이 만료되었습니다.", null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "잘못된 코드 형식입니다.", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
