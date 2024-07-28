package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getPlansByUser(User user);
}
