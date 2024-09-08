package greenjangtanji.yeosuro.plan.repository;

import greenjangtanji.yeosuro.plan.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
