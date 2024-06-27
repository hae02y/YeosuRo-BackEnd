package greenjangtanji.yeosuro.user.dto;
import greenjangtanji.yeosuro.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Boolean agree;
    private String profileImageUrl;
    private Enum role;

    public UserResponseDto (User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.agree = user.getAgree();
        this.profileImageUrl = user.getProfileImageUrl();
        this.role = user.getRole();
    }
}
