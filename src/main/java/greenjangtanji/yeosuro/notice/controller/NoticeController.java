package greenjangtanji.yeosuro.notice.controller;

import greenjangtanji.yeosuro.notice.dto.NoticeRequestDto;
import greenjangtanji.yeosuro.notice.dto.NoticeResponseDto;
import greenjangtanji.yeosuro.notice.service.NoticeService;
import greenjangtanji.yeosuro.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
@Validated
public class NoticeController {
    private final UserService userService;
    private final NoticeService noticeService;

    //공지사항 생성
    @PostMapping
    public ResponseEntity postNotice(@Valid @RequestBody NoticeRequestDto.Post postDto,
                                        Authentication authentication) {
        Long userId = userService.extractUserId(authentication);
        NoticeResponseDto.DetailNotice noticeResponseDto = noticeService.createNotice(userId, postDto);

        return new ResponseEntity<>(noticeResponseDto, HttpStatus.OK);
    }

    //공지사항 전체 조회
    @GetMapping
    public ResponseEntity getAllNotice (){
        List<NoticeResponseDto.BriefNotice> allNoticeList = noticeService.findAll();
        return new ResponseEntity<>(allNoticeList, HttpStatus.OK);
    }

    //공지사항 상세 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity getNoticeById (@PathVariable("noticeId") Long noticeId){
        NoticeResponseDto.DetailNotice noticeResponseDto = noticeService.findById(noticeId);

        return new ResponseEntity(noticeResponseDto, HttpStatus.OK);
    }

    //공지사항 수정
    @PatchMapping("/{noticeId}")
    public ResponseEntity updateNotice (@PathVariable("noticeId") Long noticeId,
                                        @Valid @RequestBody NoticeRequestDto.Patch patchDto,
                                        Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        NoticeResponseDto.DetailNotice noticeResponseDto = noticeService.updateNotice(userId,noticeId, patchDto);

        return new ResponseEntity<>(noticeResponseDto, HttpStatus.OK);
    }

    //공지사항 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity deleteNotice (@PathVariable("noticeId") Long noticeId,
                                        Authentication authentication){
        Long userId = userService.extractUserId(authentication);
        noticeService.deleteNotice(userId,noticeId);
        return ResponseEntity.ok().build();
    }

}
