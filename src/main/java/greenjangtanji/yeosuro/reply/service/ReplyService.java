package greenjangtanji.yeosuro.reply.service;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.repository.FeedRepository;
import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
import greenjangtanji.yeosuro.reply.dto.ReplyResponseDto;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.reply.dto.ReplyRequestDto;
import greenjangtanji.yeosuro.reply.entity.Reply;
import greenjangtanji.yeosuro.reply.repository.ReplyRepository;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    //댓글 생성
    public Reply createReply (Long userId, ReplyRequestDto.Post requestDto){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        Feed feed = feedRepository.findById(requestDto.getFeedId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        Reply reply = Reply.createReply(requestDto, user, feed);
        return replyRepository.save(reply);
    }

    //댓글 조회 (특정 게시글에 달린 모든 댓글 조회)
    public List<ReplyResponseDto> getRepliesByFeedId(Long feedId){
        try {
            List<Reply> replyList = replyRepository.findByFeedId(feedId);
            List<ReplyResponseDto> responseDtos = new ArrayList<>();

            for (Reply reply : replyList){
                responseDtos.add(new ReplyResponseDto(
                        reply,
                        imageService.getProfileImage(reply.getUser().getId(), ImageType.PROFILE)
                ));
            }
            return responseDtos;
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
        }

    }

    //댓글 수정
    @Transactional
    public Reply updateReply (Long id, ReplyRequestDto.Patch requestDto){
        Reply existingReply = replyRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND));

        existingReply.updateReply(requestDto.getContent());
        return existingReply;
    }

    //댓글 삭제
    @Transactional
    public Long deleteReply (Long id){
        Reply existingReply = replyRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.REPLY_NOT_FOUND));

        replyRepository.deleteById(id);
        return id;
    }
}
