package greenjangtanji.yeosuro.feed.service;

import greenjangtanji.yeosuro.feed.dto.FeedListResponseDto;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import greenjangtanji.yeosuro.feed.repository.FeedRepository;
import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.point.service.PointService;
import greenjangtanji.yeosuro.user.entity.User;
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
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final PointService pointService;

    //게시글 생성
    public Feed createFeed (Long userId, FeedRequestDto.Post requestDto){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저 정보가 없습니다.")
        );
        Feed feed = Feed.createFeed(requestDto, user);
        feedRepository.save(feed);
        pointService.feedPoint(userId, feed);
        
        return feed;
    }
    //모든 게시글 조회(최신순으로)
    public List<FeedListResponseDto> findAll() {
        try {
            List<Feed> feedList = feedRepository.findAll();
            List<FeedListResponseDto> responseDtos = new ArrayList<>();

            for (Feed feed : feedList){
                responseDtos.add(new FeedListResponseDto(feed));
            }
            return responseDtos;
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND);
        }
    }

    //카테고리 별 게시글 조회
    public List<FeedListResponseDto> getFeedsByCategory (FeedCategory feedCategory){
        try {
            List<Feed> feedList = feedRepository.findByFeedCategory(feedCategory);
            List<FeedListResponseDto> responseDtos = new ArrayList<>();

            for (Feed feed : feedList){
                responseDtos.add(new FeedListResponseDto(feed));
            }
            return responseDtos;
        }catch (Exception e){
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }
    }


    //특정 게시글 조회
    public Feed findById(Long id){

        Feed feed = feedRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        return feed;
    }

    //게시글 수정
    @Transactional
    public Feed updatePost(Long id, FeedRequestDto.Patch requestDto) {
        Feed existingFeed = feedRepository.findById(id).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        //업데이트할 내용 확인
        if (requestDto.getTitle() != null){
            existingFeed.updateTitle(requestDto.getTitle());
        }
        if (requestDto.getContent() != null){
            existingFeed.updateContent(requestDto.getContent());
        }
        if (requestDto.getImageUrl() != null){
            existingFeed.updateImage(requestDto.getImageUrl());
        }
        if (requestDto.getFeedCategory() != null){
            existingFeed.updateCategory(FeedCategory.valueOf(requestDto.getFeedCategory()));
        }

        return existingFeed;
    }

    //게시글 삭제
    @Transactional
    public Long deleteFeed (Long id) {
        feedRepository.deleteById(id);
        return id;
    }
}
