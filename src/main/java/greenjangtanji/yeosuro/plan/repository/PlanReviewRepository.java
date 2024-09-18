package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import greenjangtanji.yeosuro.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanReviewRepository extends JpaRepository<PlanReview, Long> {
    List<PlanReview> getPlansByUser(User user);
    List<PlanReview> getPlanReviewByPlan(Plan plan);
}
