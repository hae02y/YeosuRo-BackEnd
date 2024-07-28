package greenjangtanji.yeosuro.global.mail.controller;

import greenjangtanji.yeosuro.global.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private int number;

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestParam String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            String trimmedMail = mail.trim();
            number = mailService.sendMail(trimmedMail);
            String num = String.valueOf(number);

            map.put("success", Boolean.TRUE);
            map.put("number", num);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String code) {
        
        boolean isMatch = code.equals(String.valueOf(number));

        return ResponseEntity.ok(isMatch);
    }
}
