package greenjangtanji.yeosuro.site.repository;

import greenjangtanji.yeosuro.site.entity.SiteReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteReviewRepository extends JpaRepository<SiteReview, Long> {

}
