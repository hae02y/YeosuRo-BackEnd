package greenjangtanji.yeosuro.site.repository;

import greenjangtanji.yeosuro.plan.entity.PlanReview;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.site.entity.SiteReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteReviewRepository extends JpaRepository<SiteReview, Long> {

    Optional<SiteReview> findBySiteAndPlanReview(Site site, PlanReview planReview);
}
