package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.PlanReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanReviewRepository extends JpaRepository<PlanReview, Long> {
}
