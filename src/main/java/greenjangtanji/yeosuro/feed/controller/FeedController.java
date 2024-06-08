package greenjangtanji.yeosuro.feed.controller;

import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import greenjangtanji.yeosuro.feed.dto.FeedDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.mapper.FeedMapper;
import greenjangtanji.yeosuro.feed.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
@Validated
public class FeedController {
    private final FeedService feedService;
    public final FeedMapper mapper;

    //게시글 등록
    @PostMapping
    public ResponseEntity postFeed (@Valid @RequestBody FeedDto.Post postDto){
        Feed createFeed = feedService.createFeed(postDto);
        return new ResponseEntity<>(mapper.feedToFeedResponseDto(createFeed), HttpStatus.OK);

    }

    //게시글 수정

    //게시글 조회

    //게시글 삭제

}
