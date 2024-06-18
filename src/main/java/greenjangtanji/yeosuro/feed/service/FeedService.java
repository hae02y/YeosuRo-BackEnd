package greenjangtanji.yeosuro.feed.service;

import greenjangtanji.yeosuro.feed.dto.FeedListResponseDto;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.feed.dto.FeedResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.repository.FeedRepository;
import greenjangtanji.yeosuro.member.entity.Member;
import greenjangtanji.yeosuro.member.repostory.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final MemberRepository memberRepository;

    //게시글 생성
    public Feed createFeed (FeedRequestDto.Post requestDto){
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("유저 정보가 없습니다.")
        );
        Feed feed = Feed.createFeed(requestDto, member);
        return feedRepository.save(feed);
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

        }
        return null;
    }

    //특정 게시글 조회
    public Feed findById(Long id){
        Feed feed = feedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("조회 실패"));

        return feed;
    }

    //게시글 수정
    @Transactional
    public Feed updatePost(Long id, FeedRequestDto.Patch requestDto) {
        Feed existingFeed = feedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다")
        );
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

        return existingFeed;
    }

    //게시글 삭제
    @Transactional
    public Long deleteFeed (Long id) {
        feedRepository.deleteById(id);
        return id;
    }
}
