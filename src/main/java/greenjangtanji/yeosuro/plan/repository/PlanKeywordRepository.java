package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.PlanKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanKeywordRepository extends JpaRepository<PlanKeyword, Long> {
}
