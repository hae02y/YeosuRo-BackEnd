package greenjangtanji.yeosuro.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class SignUp {
        @NotBlank
        private String email;
        @NotBlank
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,16}",
        message = "8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요")
        private String password;
        @NotBlank
        private String nickname;
        @NotBlank
        private Boolean agree;
    }

    @Getter
    @NoArgsConstructor
    public static class AdditionalInformation {
        @NotBlank
        private Boolean agree;
    }

    @Getter
    @NoArgsConstructor
    public static class Patch {
        private String nickname;
        private String profileImageUrl;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdatePassword {
        @NotBlank
        private String email;

        @NotBlank
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,16}",
                message = "8~16자 영문 대소문자, 숫자, 특수문자를 사용하세요")
        private String password;
    }
}
