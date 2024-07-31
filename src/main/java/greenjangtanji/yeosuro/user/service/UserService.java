package greenjangtanji.yeosuro.user.service;

import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.global.exception.InvalidTokenException;
import greenjangtanji.yeosuro.point.entity.Tier;
import greenjangtanji.yeosuro.user.dto.UserRequestDto;
import greenjangtanji.yeosuro.user.entity.Role;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.entity.UserStatus;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

    public User createMember(UserRequestDto.SignUp signUp) throws Exception{

        checkNickname(signUp.getNickname());
        checkEmail(signUp.getEmail());

        User user = User.builder()
                .email(signUp.getEmail())
                .password(signUp.getPassword())
                .nickname(signUp.getNickname())
                .profileImageUrl("기본이미지 주소")
                .agree(signUp.getAgree())
                .role(Role.USER)
                .userStatus(UserStatus.ACTIVE)
                .totalPoint(0)
                .tier(Tier.SILVER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        return user;
    }
    //회원정보 조회
    public User getUserInfo(Long userId)throws Exception{
        User user = checkUser(userId);
        return user;
    }

    //회원정보 수정
    @Transactional
    public User updateUserInfo (Long userId, UserRequestDto.Patch patch) throws Exception {
        User user =  checkUser(userId);

        if (patch.getNickname() != null){
            user.updateNickname(patch.getNickname());
        }
        if (patch.getProfileImageUrl() != null){
            user.updateProfileImageUrl(patch.getProfileImageUrl());
        }

        return user;
    }

    //회원 탈퇴


    //회원 확인
    private User checkUser (Long userId){
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return existingUser;
    }

    //닉네님 중복확인
    private void checkNickname (String nickname)throws Exception{
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()){
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME_ERROR);
        }
    }

    //이메일 중복확인
    private void checkEmail (String email) throws Exception{
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL_ERROR);
        }
    }

    public Long extractUserId (Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidTokenException("Invalid memberId format");
        }
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        return user.get().getId();
    }


}
