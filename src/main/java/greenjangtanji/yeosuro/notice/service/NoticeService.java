package greenjangtanji.yeosuro.notice.service;

import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.notice.dto.NoticeRequestDto;
import greenjangtanji.yeosuro.notice.dto.NoticeResponseDto;
import greenjangtanji.yeosuro.notice.entity.Notice;
import greenjangtanji.yeosuro.notice.repository.NoticeRepository;
import greenjangtanji.yeosuro.user.entity.Role;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final UserService userService;
    private final NoticeRepository noticeRepository;

    //공지사항 생성
    public NoticeResponseDto.DetailNotice createNotice (Long userId, NoticeRequestDto.Post requestDto) {
        User user = userService.getUserInfo(userId);
        checkAdminRole(user);
        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        noticeRepository.save(notice);
        return new NoticeResponseDto.DetailNotice(notice);
    }

    //공지사항 전체 조회
    public List<NoticeResponseDto.BriefNotice> findAll (){
        List<Notice> noticeList = noticeRepository.findAllByOrderByCreateAtDesc();
        List<NoticeResponseDto.BriefNotice> responseDtoList = new ArrayList<>();

        for (Notice notice : noticeList){
            responseDtoList.add(new NoticeResponseDto.BriefNotice(notice));
        }
        return responseDtoList;
    }

    //공지사항 상세 조회
    public NoticeResponseDto.DetailNotice findById (Long noticeId){
        Notice notice = checkNotice(noticeId);
        return new NoticeResponseDto.DetailNotice(notice);
    }

    //공지사항 수정
    @Transactional
    public NoticeResponseDto.DetailNotice updateNotice (Long userId,Long noticeId, NoticeRequestDto.Patch patchDto){
        User user = userService.getUserInfo(userId);
        checkAdminRole(user);
        Notice existingNotice = checkNotice(noticeId);

        if (patchDto.getTitle() != null){
            existingNotice.updateTitle(patchDto.getTitle());
        }
        if (patchDto.getContent() != null){
            existingNotice.updateContent(patchDto.getContent());
        }
        return new NoticeResponseDto.DetailNotice(existingNotice);
    }

    //공지사항 삭제
    @Transactional
    public void deleteNotice (Long userId, Long noticeId){
        User user = userService.getUserInfo(userId);
        checkAdminRole(user);
        checkNotice(noticeId);
        noticeRepository.deleteById(noticeId);
    }

    //권한 확인
    private void checkAdminRole (User user){
        if (user.getRole() == Role.GUEST || user.getRole() == Role.USER ){
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }
    }

    //공지사항이 존재하는지 확인
    private Notice checkNotice (Long noticeId){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return notice;
    }

}
