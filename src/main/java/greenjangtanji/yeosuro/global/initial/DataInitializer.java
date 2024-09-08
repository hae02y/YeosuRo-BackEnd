package greenjangtanji.yeosuro.global.initial;

import greenjangtanji.yeosuro.plan.entity.Keyword;
import greenjangtanji.yeosuro.plan.entity.KeywordType;
import greenjangtanji.yeosuro.plan.repository.KeywordRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private KeywordRepository keywordRepository;

    @PostConstruct
    public void init() {
        if (keywordRepository.count() != KeywordType.values().length) { // 중복 삽입 방지
            keywordRepository.deleteAll();
            keywordRepository.save(new Keyword(KeywordType.NATURE));
            keywordRepository.save(new Keyword(KeywordType.EAT));
            keywordRepository.save(new Keyword(KeywordType.SPORTS));
            keywordRepository.save(new Keyword(KeywordType.REST));
        }
    }
}
