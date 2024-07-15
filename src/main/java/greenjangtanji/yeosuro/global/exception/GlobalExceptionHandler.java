package greenjangtanji.yeosuro.global.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessLogicException (BusinessLogicException e){
        Map<String, Object> response = new HashMap<>();
        ExceptionCode exceptionCode = e.getExceptionCode();
        response.put("errorCode", exceptionCode.getStatus());
        response.put("message", exceptionCode.getMessage());

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(exceptionCode.getStatus()));
    }

    @ExceptionHandler(value = { InvalidTokenException.class })
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", ExceptionCode.JWT_TOKEN_ERROR.getStatus());
        body.put("message",ExceptionCode.JWT_TOKEN_ERROR.getMessage());


        return new ResponseEntity<>(body, HttpStatusCode.valueOf(ExceptionCode.JWT_TOKEN_ERROR.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");

        // 유효성 검사 오류 정보 설정
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }
}