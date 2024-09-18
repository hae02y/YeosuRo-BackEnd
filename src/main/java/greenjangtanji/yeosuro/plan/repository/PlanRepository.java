package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getPlansByUser(User user);
    Plan getPlanById(Long id);
}
