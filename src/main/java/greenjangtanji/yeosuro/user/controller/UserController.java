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


    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserRequestDto.SignUpDto signUpDto) throws Exception {
        User user = userService.createMember(signUpDto);
        UserResponseDto responseDto = new UserResponseDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
