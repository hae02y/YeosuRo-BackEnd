package greenjangtanji.yeosuro.feed.service;

import greenjangtanji.yeosuro.feed.dto.FeedListResponseDto;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.feed.dto.FeedResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import greenjangtanji.yeosuro.feed.repository.FeedRepository;
import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
import greenjangtanji.yeosuro.point.service.PointService;
import greenjangtanji.yeosuro.reply.dto.ReplyResponseDto;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final PointService pointService;
    private final ImageService imageService;

    // 게시글 생성
    public FeedResponseDto createFeed(Long userId, FeedRequestDto.Post requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
        );
        Feed feed = Feed.createFeed(requestDto, user);
        feedRepository.save(feed);
        // 이미지 URL이 null일 경우 빈 리스트로 초기화
        List<String> imageUrls = requestDto.getImageUrls() != null ? requestDto.getImageUrls() : new ArrayList<>();

        imageService.updateReferenceIdAndType(feed.getId(), ImageType.FEED, imageUrls);
        pointService.feedPoint(userId, feed);

        return createFeedResponseDto(feed);
    }

    // 모든 게시글 조회(최신순으로)
    public List<FeedResponseDto> findAll() {
        try {
            List<Feed> feedList = feedRepository.findAll();
            return feedList.stream()
                    .map(this::createFeedResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
        }
    }

    // 카테고리 별 게시글 조회
    public List<FeedResponseDto> getFeedsByCategory(FeedCategory feedCategory) {
        try {
            List<Feed> feedList = feedRepository.findByFeedCategory(feedCategory);
            return feedList.stream()
                    .map(this::createFeedResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }
    }

    // 특정 게시글 조회
    public FeedResponseDto findById(Long id) {
        Feed feed = feedRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return createFeedResponseDto(feed);
    }

    @Transactional
    public FeedResponseDto updatePost(Long id, FeedRequestDto.Patch requestDto) {
        Feed existingFeed = feedRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        if (requestDto.getTitle() != null) {
            existingFeed.updateTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null) {
            existingFeed.updateContent(requestDto.getContent());
        }
        if (requestDto.getImageUrls() != null) {
            imageService.updateReferenceIdAndType(existingFeed.getId(), ImageType.FEED, requestDto.getImageUrls());
        }
        if (requestDto.getFeedCategory() != null) {
            existingFeed.updateCategory(FeedCategory.valueOf(requestDto.getFeedCategory()));
        }

        return createFeedResponseDto(existingFeed);
    }

    private FeedResponseDto createFeedResponseDto(Feed feed) {
        List<String> imageUrls = imageService.getImagesByReferenceIdAndType(feed.getId(), ImageType.FEED);

        List<ReplyResponseDto> replies = feed.getReplies().stream()
                .map(reply -> new ReplyResponseDto(reply))
                .collect(Collectors.toList());

        return new FeedResponseDto(feed, imageUrls,replies);
    }

    // 게시글 삭제
    @Transactional
    public Long deleteFeed(Long id) {
        feedRepository.deleteById(id);
        return id;
    }
}
