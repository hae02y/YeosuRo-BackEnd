package greenjangtanji.yeosuro.user.service;

import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.global.exception.InvalidTokenException;
import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
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
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    public User createMember(UserRequestDto.SignUp signUp){
        User user = checkUserStatus(signUp);
        if (user == null) {
            return postUser(signUp);
        }
        return user;
    }

    //회원정보 조회
    public User getUserInfo(Long userId){
        User user =  checkUser(userId);

        return user;
    }

    //회원정보 수정
    @Transactional
    public User updateUserInfo (Long userId, UserRequestDto.Patch patch) {
        User user =  checkUser(userId);

        if (patch.getNickname() != null){
            user.updateNickname(patch.getNickname());
        }
        if (patch.getProfileImageUrl() != null){
            imageService.updateProfileImage(userId, ImageType.PROFILE, patch.getProfileImageUrl());
            user.updateProfileImage(patch.getProfileImageUrl());
        }

        return user;

    }

    //회원 탈퇴
    public boolean deactivateMember (Long userId){
        User user = checkUser(userId);
        if (user.getUserStatus() == UserStatus.ACTIVE){
            user.updateUserStatus(UserStatus.INACTIVE);
            return true;
        }
        return false;
    }

    //비밀번호 변경
    @Transactional
    public void updatePassword (String email, String newPassword){
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    //회원 생성
    private User postUser (UserRequestDto.SignUp signUp){
        checkNickname(signUp.getNickname());
        checkEmail(signUp.getEmail());

        User user = User.builder()
                .email(signUp.getEmail())
                .password(signUp.getPassword())
                .nickname(signUp.getNickname())
                .profileImageUrl(imageService.getDefaultImageUrl())
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

    //회원 상태 확인
    private User checkUserStatus(UserRequestDto.SignUp signUp) {
        Optional<User> existingUser = userRepository.findByEmail(signUp.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // 기존 유저가 INACTIVE 상태인지 확인
            if (user.getUserStatus() == UserStatus.INACTIVE) {
                user.updateUserStatus(UserStatus.ACTIVE);
                user.updatePassword(passwordEncoder.encode(signUp.getPassword()));
                user.updateNickname(signUp.getNickname());
                user.updateAgree(signUp.getAgree());
                user.updateTier(Tier.SILVER);
                user.updateTotalPoint(0);
                user.updateProfileImage(imageService.getDefaultImageUrl());
                return user;
            } else {
                throw new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL_ERROR);
            }
        }
        return null;
    }

    //닉네님 중복확인
    private void checkNickname (String nickname){
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()){
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_NICKNAME_ERROR);
        }
    }

    //이메일 중복확인
    private void checkEmail (String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_EMAIL_ERROR);
        }
    }

    //활성화 상태인 회원 확인
    private User checkUser (Long userId){
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        if (existingUser.getUserStatus() != UserStatus.ACTIVE) {
            throw new BusinessLogicException(ExceptionCode.USER_NOT_ACTIVE);
        }

        return existingUser;
    }

    public Long extractUserId (Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidTokenException("Invalid memberId format");
        }
        Optional<User> user = userRepository.findByEmail(authentication.getName());
        return user.get().getId();
    }


}
