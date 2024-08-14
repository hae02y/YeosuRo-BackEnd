package greenjangtanji.yeosuro.user.controller;

import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
import greenjangtanji.yeosuro.user.dto.UserRequestDto;
import greenjangtanji.yeosuro.user.dto.UserResponseDto;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import jakarta.validation.Valid;
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


    //자체 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@Valid @RequestBody UserRequestDto.SignUp signUp) {
        User user = userService.createMember(signUp);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }

    //비밀번호 변경
    @PatchMapping("/sign-up/password")
    public ResponseEntity updatePasswordInfo (@Valid @RequestBody UserRequestDto.UpdatePassword updatePassword){
        userService.updatePassword(updatePassword.getEmail(), updatePassword.getPassword());

        return new ResponseEntity(HttpStatus.OK);
    }


    //회원 정보 수정
    @PatchMapping ("/users")
    public ResponseEntity updateUserInfo (@Valid @RequestBody UserRequestDto.Patch patchDto,
                                          Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        User user = userService.updateUserInfo(userId, patchDto);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }

    //회원정보 조회
    @GetMapping("/users")
    public ResponseEntity getUserInfo (Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        User user = userService.getUserInfo(userId);
        UserResponseDto.DetailUserInfo detailUserInfo = new UserResponseDto.DetailUserInfo(user);

        return new ResponseEntity<>(detailUserInfo, HttpStatus.OK);
    }


    //소셜 회원가입 유저 추가 정보 등록
    @PostMapping("additional-info")
    public ResponseEntity additionalUserInfo (@Valid @RequestBody UserRequestDto.AdditionalInformation additionalInformation){

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //회원 탈퇴
    @PatchMapping("/users/deactivate")
    public ResponseEntity<String> deactivateUser (Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        boolean isDeactivated = userService.deactivateMember(userId);
        if (isDeactivated) {
            return ResponseEntity.ok("회원 비활성화 성공");
        }else {
            return ResponseEntity.status(404).body("이미 탈퇴한 회원입니다");
        }
    }



}
