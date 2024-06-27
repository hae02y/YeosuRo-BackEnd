package greenjangtanji.yeosuro.user.service;

import greenjangtanji.yeosuro.user.dto.UserRequestDto;
import greenjangtanji.yeosuro.user.entity.Role;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createMember(UserRequestDto.SignUpDto signUpDto) throws Exception{

        checkNickname(signUpDto.getNickname());
        checkEmail(signUpDto.getEmail());

        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .nickname(signUpDto.getNickname())
                .profileImageUrl("기본이미지 주소")
                .agree(signUpDto.getAgree())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        return user;
    }

    //닉네님 중복확인
    private void checkNickname (String nickname)throws Exception{
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()){
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
    }

    //이메일 중복확인
    private void checkEmail (String email) throws Exception{
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }
    }


}
