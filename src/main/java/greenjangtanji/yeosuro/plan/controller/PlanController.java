package greenjangtanji.yeosuro.plan.controller;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.dto.PlanReviewDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import greenjangtanji.yeosuro.plan.mapper.PlanMapper;
import greenjangtanji.yeosuro.plan.service.PlanService;
import greenjangtanji.yeosuro.user.entity.User;
import greenjangtanji.yeosuro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/plans")
public class PlanController {

    private final UserService userService;
    private final PlanService planService;
    private final PlanMapper planMapper;

    @Autowired
    public PlanController(UserService userService, PlanService planService, PlanMapper planMapper) {
        this.userService = userService;
        this.planService = planService;
        this.planMapper = planMapper;
    }

    // 모든 여정 조회
    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getPlan(Authentication auth) {
        long id = userService.extractUserId(auth);
        List<Plan> planList = planService.getAllPlan();
        List<PlanDto.PlanResponseDto> planResponseDtoList = planMapper.planListToPlanResponseDtoList(planList);
        return new ResponseEntity<>(planResponseDtoList, HttpStatus.OK);
    }

    // 나의 여정 조회
    @GetMapping(value = {"/me"})
    public ResponseEntity<?> getPlanMe(Authentication auth) {
        long id = userService.extractUserId(auth);
        User user = userService.getUserInfo(id);
        List<Plan> planList = planService.getMyPlans(user);
        System.out.println(planList.toString());
        List<PlanDto.PlanResponseDto> planResponseDtoList = planMapper.planListToPlanResponseDtoList(planList);
        return new ResponseEntity<>(planResponseDtoList, HttpStatus.OK);
    }

    //여정 ID로 조회
    @GetMapping(value = {"/{planId}"})
    public ResponseEntity<?> getPlanByPlanId(Authentication auth, @PathVariable Long planId) {
        long id = userService.extractUserId(auth);

        Plan plan = planService.getPlanByPlanId(planId);

        PlanDto.PlanResponseDto planResponseDto = planMapper.planToPlanResponseDto(plan);
        return new ResponseEntity<>(planResponseDto, HttpStatus.OK);
    }

    // 나의 여정 등록
    @PostMapping(value = {"/", ""})
    public ResponseEntity<?> postPlan(Authentication auth, @RequestBody PlanDto.PlanPostDto planPostDto) {
        long userId = userService.extractUserId(auth);
        Plan plan = planMapper.planPostDtoToPlan(planPostDto);
        plan.setUser(userService.getUserInfo(userId));
        planService.savePlan(plan, planPostDto.getSites(),planPostDto.getImageUrls());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //나의 여정 수정 (구현중)
    @PatchMapping(value = {"/", ""})
    public ResponseEntity<?> patchPlan(Authentication auth) {
        long id = userService.extractUserId(auth);
        System.out.printf(String.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping(value = {"/{planId}"})
    public ResponseEntity<?> deletePlan(Authentication authentication, @PathVariable(value = "planId") Long planId){
        planService.deletePlanByPlanId(planId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //여정 후기 작성
    @PostMapping(value = {"/{planId}/review"})
    public ResponseEntity<?> postPlanReview(Authentication auth,
                                            @PathVariable long planId,
                                            @RequestBody PlanReviewDto.PlanReviewPostDto planReviewPostDto) {
        long id = userService.extractUserId(auth);
        PlanReview planReview = planMapper.planReviewPostDtoToPlanReview(planReviewPostDto);
        System.out.println("here" + planReviewPostDto.toString());
        System.out.println("planReview" + planReview.toString());
        planReview.setUser(userService.getUserInfo(id));
        Plan plan = planService.getPlanByPlanId(planId);
        planReview.setPlan(plan);
        planService.savePlanReview(planReview, planReviewPostDto.getSiteReviews());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //여정 후기 조회
    @GetMapping(value = {"/{planId}/review"})
    public ResponseEntity<?> getPlanReview(Authentication auth,
                                           @PathVariable long planId) {
        long id = userService.extractUserId(auth);
        List<PlanReviewDto.PlanReviewResponseDto> dto = planMapper.planReviewListToPlanReviewDtoList(planService.getPlanReviewsByPlanId(planId));

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
