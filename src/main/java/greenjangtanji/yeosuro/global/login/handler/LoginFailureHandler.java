package greenjangtanji.yeosuro.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public LoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpServletResponse.SC_BAD_REQUEST);
        responseBody.put("error", "Authentication Failure");

        String errorMessage;

        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디나 비밀번호가 올바르지 않습니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화되었습니다.";
        } else if (exception instanceof AuthenticationServiceException) {
            errorMessage = "아이디나 비밀번호의 형식이 잘못되었습니다.";
        } else {
            errorMessage = "알 수 없는 로그인 오류가 발생했습니다.";
        }

        responseBody.put("message", errorMessage);

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        log.info("로그인 실패: {}", exception.getMessage());
    }
}
