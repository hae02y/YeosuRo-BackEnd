package greenjangtanji.yeosuro.user.controller;

import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
import greenjangtanji.yeosuro.user.dto.UserRequestDto;
import greenjangtanji.yeosuro.user.dto.UserResponseDto;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;


    //자체 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserRequestDto.SignUp signUp) {
        User user = userService.createMember(signUp);
        String profileImage = imageService.getProfileImage(user.getId(), ImageType.PROFILE);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user, profileImage);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }


    //회원 정보 수정
    @PatchMapping ("/users")
    public ResponseEntity updateUserInfo (@RequestBody UserRequestDto.Patch patchDto,
                                          Authentication authentication) throws Exception {
        Long userId = userService.extractUserId(authentication);
        User user = userService.updateUserInfo(userId, patchDto);
        String profileImage = imageService.getProfileImage(user.getId(), ImageType.PROFILE);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user, profileImage);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }

    //회원정보 조회
    @GetMapping("/users")
    public ResponseEntity getUserInfo (Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        User user = userService.getUserInfo(userId);
        String profileImage = imageService.getProfileImage(user.getId(), ImageType.PROFILE);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user, profileImage);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }

    @PostMapping("additional-info")
    public ResponseEntity additionalUserInfo (@RequestBody UserRequestDto.AdditionalInformation additionalInformation){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //비밀번호 변경
    @PatchMapping("/sign-up/password")
    public ResponseEntity updatePasswordInfo (@RequestBody UserRequestDto.UpdatePassword updatePassword){
        userService.updatePassword(updatePassword.getEmail(), updatePassword.getPassword());

        return new ResponseEntity(HttpStatus.OK);
    }

    //회원 탈퇴


}
