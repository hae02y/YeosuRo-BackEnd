package greenjangtanji.yeosuro.user.controller;

import greenjangtanji.yeosuro.user.dto.UserRequestDto;
import greenjangtanji.yeosuro.user.dto.UserResponseDto;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //자체 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserRequestDto.SignUp signUp) throws Exception {
        User user = userService.createMember(signUp);
        UserResponseDto responseDto = new UserResponseDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    //회원 정보 수정
    @PatchMapping ("/users/{user-id}")
    public ResponseEntity updateUserInfo (@PathVariable("user-id") Long userId, @RequestBody UserRequestDto.Patch patchDto) throws Exception {
        User user = userService.updateUserInfo(userId, patchDto);
        UserResponseDto userResponseDto = new UserResponseDto(user);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    //회원 탈퇴

    //테스트
    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
