package greenjangtanji.yeosuro.global.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "${spring.mail.username}";
    private static int number;
    private static LocalDateTime issuanceTime; // 발급 시간

    // 랜덤으로 숫자 생성
    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000;
        issuanceTime = LocalDateTime.now(); // 발급 시간 기록
    }

    public MimeMessage CreateMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("여수로 이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";

            // 인증 번호에 테두리 스타일 추가
            body += "<div style='border: 2px solid gray; background-color: #f5f5f5; padding: 10px 20px; display: inline-block;'>";
            body += "<h1 style='color: black; margin: 0;'>" + number + "</h1>";
            body += "</div>";

            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);

        return number;
    }

    //코드 발급시간 확인
    public boolean isCodeValid(int code) {
        // 발급 시간이 3분 이내인지 확인
        if (LocalDateTime.now().isAfter(issuanceTime.plusMinutes(3))) {
            // 유효 시간이 지나면 number와 issuanceTime을 초기화
            number = -1; // 유효하지 않은 값으로 초기화
            issuanceTime = null;
            return false;
        }
        return number == code;
    }
}
