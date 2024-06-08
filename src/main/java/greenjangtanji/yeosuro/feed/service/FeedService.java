package greenjangtanji.yeosuro.feed.service;

import greenjangtanji.yeosuro.feed.dto.FeedDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.mapper.FeedMapper;
import greenjangtanji.yeosuro.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {


    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;

    //게시글 등록
    public Feed createFeed (FeedDto.Post postDto){
        Feed feed = feedMapper.feedDtoToFeed(postDto);
        return feedRepository.save(feed);
    }
    //모든 게시글 조회
    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

    //특정 게시글 조회
    public Optional<Feed> findById(Long id){
        return feedRepository.findById(id);
    }

    //게시글 수정
    public Optional<Feed> updatePost(Long id, Feed postDetails) {
        Optional<Feed> optionalPost = feedRepository.findById(id);

        if (optionalPost.isPresent()) {
            Feed post = optionalPost.get();
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            feedRepository.save(post);
            return Optional.of(post);
        } else {
            return Optional.empty();
        }
    }
    //게시글 삭제
    public void deleteById(Long id) {
        feedRepository.deleteById(id);
    }
}
