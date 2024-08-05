package greenjangtanji.yeosuro.plan.service;

import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.repository.PlanRepository;
import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.site.repository.SiteRepository;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public PlanService(PlanRepository planRepository, SiteRepository siteRepository) {
        this.planRepository = planRepository;
        this.siteRepository = siteRepository;
    }

    @Transactional
    public void savePlan(Plan savedPlan, List<SiteDto.SitePostDto> sitePostDtos) {
        planRepository.save(savedPlan);
        List<Site> sites = sitePostDtos.stream()
                .map(sitePostDto -> Site.builder()
                        .category(sitePostDto.getCategory())
                        .memo(sitePostDto.getMemo())
                        .latitude(sitePostDto.getLatitude())
                        .longitude(sitePostDto.getLongitude())
                        .address(sitePostDto.getAddress())
                        .visitDate(sitePostDto.getVisitDate())
                        .plan(savedPlan)
                        .build())
                .toList();

        siteRepository.saveAll(sites);
    }

    public List<Plan> getAllPlan() {
        return planRepository.findAll();
    }

    public List<Plan> getMyPlans(User user) {
        return planRepository.getPlansByUser(user);
    }
}
