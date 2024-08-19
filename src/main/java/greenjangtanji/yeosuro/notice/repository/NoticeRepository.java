package greenjangtanji.yeosuro.notice.repository;

import greenjangtanji.yeosuro.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByOrderByCreateAtDesc();
}
