package greenjangtanji.yeosuro.global.initial;

import greenjangtanji.yeosuro.plan.entity.Keyword;
import greenjangtanji.yeosuro.plan.entity.PlanKeyword;
import greenjangtanji.yeosuro.plan.repository.PlanKeywordRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private PlanKeywordRepository planKeywordRepository;

    @PostConstruct
    public void init() {
        if (planKeywordRepository.count() != Keyword.values().length) { // 중복 삽입 방지
            planKeywordRepository.deleteAll();
            planKeywordRepository.save(new PlanKeyword(Keyword.NATURE));
            planKeywordRepository.save(new PlanKeyword(Keyword.EAT));
            planKeywordRepository.save(new PlanKeyword(Keyword.SPORTS));
            planKeywordRepository.save(new PlanKeyword(Keyword.REST));
        }
    }
}
