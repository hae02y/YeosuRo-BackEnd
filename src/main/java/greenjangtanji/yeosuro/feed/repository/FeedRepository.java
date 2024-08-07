package greenjangtanji.yeosuro.feed.repository;

import greenjangtanji.yeosuro.feed.dto.FeedListResponseDto;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

   List <FeedListResponseDto> findAllByOrderByCreateAtDesc();
   List<Feed> findByFeedCategory(FeedCategory feedCategory);

   @Query("SELECT f FROM Feed f LEFT JOIN FETCH f.replies WHERE f.id = :id")
   Optional<Feed> findByIdWithReplies(@Param("id") Long id);
}
