package greenjangtanji.yeosuro.site.service;

import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.site.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public List<Site> getAllSites(){
        return siteRepository.findAll();
    }

    public Site getSite(Long siteId){
        return siteRepository.findById(siteId).orElseThrow(() -> new RuntimeException("Site를 찾을수 없습니다."));
    }
}
