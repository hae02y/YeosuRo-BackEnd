package greenjangtanji.yeosuro.plan.service;

import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import greenjangtanji.yeosuro.plan.repository.PlanRepository;
import greenjangtanji.yeosuro.plan.repository.PlanReviewRepository;
import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.dto.SiteReviewDto;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.site.entity.SiteReview;
import greenjangtanji.yeosuro.site.repository.SiteRepository;
import greenjangtanji.yeosuro.site.repository.SiteReviewRepository;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SiteRepository siteRepository;
    private final PlanReviewRepository planReviewRepository;
    private final SiteReviewRepository siteReviewRepository;

    @Autowired
    public PlanService(PlanRepository planRepository, SiteRepository siteRepository, PlanReviewRepository planReviewRepository, SiteReviewRepository siteReviewRepository) {
        this.planRepository = planRepository;
        this.siteRepository = siteRepository;
        this.planReviewRepository = planReviewRepository;
        this.siteReviewRepository = siteReviewRepository;
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

    public Plan getPlanByPlanId(Long planId) {
        return planRepository.findById(planId).orElse(null);
    }

    public List<Plan> getMyPlans(User user) {
        return planRepository.getPlansByUser(user);
    }

    @Transactional
    public void savePlanReview(PlanReview planReview, List<SiteReviewDto.SiteReviewPostDto> siteReviewPostDtos) {
        planReviewRepository.save(planReview);

        List<SiteReview> siteReviews = siteReviewPostDtos.stream()
                .map(siteReviewPostDto -> {
                    Site site = siteRepository.findById(siteReviewPostDto.getSiteId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid site ID: " + siteReviewPostDto.getSiteId()));

                    // 중복된 SiteReview 체크
                    Optional<SiteReview> existingReviewOpt = siteReviewRepository.findBySiteAndPlanReview(site, planReview);
                    if (existingReviewOpt.isPresent()) {
                        // 이미 존재하는 리뷰가 있으면 내용을 업데이트
                        SiteReview existingReview = existingReviewOpt.get();
                        existingReview.setContents(siteReviewPostDto.getContents());
                        return existingReview;
                    }
                    // 새로운 SiteReview 생성
                    return SiteReview.builder()
                            .contents(siteReviewPostDto.getContents())
                            .planReview(planReview)
                            .site(site)
                            .build();
                })
                .toList();

        siteReviewRepository.saveAll(siteReviews);
    }



    public List<PlanReview> getAllPlanReviews() {
        return planReviewRepository.findAll();
    }

    public List<PlanReview> getMyPlanReviews(User user) {
        return planReviewRepository.getPlansByUser(user);
    }
}
