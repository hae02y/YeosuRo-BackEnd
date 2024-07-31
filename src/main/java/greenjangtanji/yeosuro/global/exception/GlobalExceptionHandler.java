package greenjangtanji.yeosuro.global.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직 예외 처리
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessLogicException(BusinessLogicException e) {
        Map<String, Object> response = new HashMap<>();
        ExceptionCode exceptionCode = e.getExceptionCode();
        response.put("errorCode", exceptionCode.getStatus());
        response.put("message", exceptionCode.getMessage());

        return new ResponseEntity<>(response, HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    // 유효하지 않은 토큰 예외 처리
    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", ExceptionCode.JWT_TOKEN_ERROR.getStatus());
        body.put("message", ExceptionCode.JWT_TOKEN_ERROR.getMessage());

        return new ResponseEntity<>(body, HttpStatus.valueOf(ExceptionCode.JWT_TOKEN_ERROR.getStatus()));
    }

    // 유효성 검사 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Error");

        // 유효성 검사 오류 메시지 수집
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    // 잘못된 매개변수 타입 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid Parameter");

        String parameterName = ex.getName(); // 잘못된 파라미터 이름
        String parameterType = ex.getRequiredType().getSimpleName(); // 기대되는 파라미터 타입
        String valueType = (ex.getValue() != null) ? ex.getValue().getClass().getSimpleName() : "null"; // 실제 전달된 값의 타입

        String message = String.format("Parameter '%s' should be of type %s, but received type %s",
                parameterName, parameterType, valueType);

        response.put("message", message);

        return ResponseEntity.badRequest().body(response);
    }

    // JSON 파싱 오류 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid Request Body");

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException formatException = (InvalidFormatException) cause;
            String field = formatException.getPath().get(0).getFieldName(); // 잘못된 필드 이름
            String targetType = formatException.getTargetType().getSimpleName(); // 기대되는 타입
            String valueType = (formatException.getValue() != null) ? formatException.getValue().getClass().getSimpleName() : "null"; // 실제 전달된 값의 타입

            String message = String.format("Field '%s' should be of type %s, but received type %s",
                    field, targetType, valueType);

            response.put("message", message);
        } else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mismatchException = (MismatchedInputException) cause;
            String field = mismatchException.getPath().isEmpty() ? "unknown" : mismatchException.getPath().get(0).getFieldName();
            String targetType = mismatchException.getTargetType().getSimpleName();
            String message = String.format("Invalid value for field '%s'. Expected type: %s",
                    field, targetType);

            response.put("message", message);
        } else {
            response.put("message", "Invalid JSON format or content. Please check the request payload.");
        }

        return ResponseEntity.badRequest().body(response);
    }

    // Enum 값 오류 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleEnumException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Invalid Enum Value");

        String message = ex.getMessage() != null ? ex.getMessage() : "Provided value does not match any enum constants.";
        response.put("message", message);

        return ResponseEntity.badRequest().body(response);
    }

    // 존재하지 않는 엔드포인트 요청 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", "Endpoint '" + ex.getRequestURL() + "' not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", "An unknown error occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
