package greenjangtanji.yeosuro.feed.controller;

import greenjangtanji.yeosuro.feed.dto.FeedListResponseDto;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.feed.dto.FeedResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import greenjangtanji.yeosuro.feed.service.FeedService;
import greenjangtanji.yeosuro.global.exception.BusinessLogicException;
import greenjangtanji.yeosuro.global.exception.ExceptionCode;
import greenjangtanji.yeosuro.image.entity.ImageType;
import greenjangtanji.yeosuro.image.service.ImageService;
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
@RequestMapping("/feeds")
@RequiredArgsConstructor
@Validated
public class FeedController {
    private final FeedService feedService;
    private final UserService userService;
    private final ImageService imageService;

    //게시글 등록
    @PostMapping
    public ResponseEntity postFeed (@Valid @RequestBody FeedRequestDto.Post postDto, Authentication authentication) {
        Long userId = userService.extractUserId(authentication);
        Feed feed = feedService.createFeed(userId,postDto);
        List<String> imageList = imageService.getImagesByReferenceIdAndType(feed.getId(), ImageType.FEED);
        FeedResponseDto responseDto = new FeedResponseDto(feed, imageList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity getAllFeed ( ){
        List<FeedListResponseDto> allFeedList = feedService.findAll();

        return new ResponseEntity<>(allFeedList, HttpStatus.OK);
    }
    //카테고리 별 게시글 조회
    @GetMapping ("category/{category}")
    public ResponseEntity getFeedByCategory (@PathVariable("category") String category){

        try {
            FeedCategory feedCategory = FeedCategory.valueOf(category.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND);
        }
        List<FeedListResponseDto> feedList = feedService.getFeedsByCategory(FeedCategory.valueOf(category.toUpperCase()));

        return new ResponseEntity<>(feedList, HttpStatus.OK);
    }

    //특정 게시글 조회
    @GetMapping("{feed-id}")
    public ResponseEntity getFeed (@PathVariable("feed-id") Long feedId){
        FeedResponseDto feedResponseDto = feedService.findById(feedId);

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);

    }

    //게시글 수정
    @PatchMapping("{feed-id}")
    public ResponseEntity patchFeed (@PathVariable("feed-id") Long feedId, @RequestBody FeedRequestDto.Patch requestDto){
        FeedResponseDto feedResponseDto  = feedService.updatePost(feedId, requestDto);

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("{feed-id}")
    public ResponseEntity deleteFeed (@PathVariable("feed-id") Long feedId){
        feedService.deleteFeed(feedId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
