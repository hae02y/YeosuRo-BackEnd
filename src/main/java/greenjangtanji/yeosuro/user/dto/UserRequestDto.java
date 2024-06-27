package greenjangtanji.yeosuro.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpDto {
        private String email;
        private String password;
        private String nickname;
        private Boolean agree;
    }
}
