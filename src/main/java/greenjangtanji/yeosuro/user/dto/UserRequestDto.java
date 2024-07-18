package greenjangtanji.yeosuro.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class SignUp {
        @NotBlank
        private String email;
        @NotBlank
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
}
