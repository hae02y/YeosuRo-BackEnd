package greenjangtanji.yeosuro.plan.controller;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
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
    public ResponseEntity<?> getPlan(Authentication auth) throws Exception {
        long id = userService.extractUserId(auth);
        List<Plan> planList = planService.getAllPlan();
        List<PlanDto.PlanResponseDto> planResponseDtoList = planMapper.planListToPlanResponseDtoList(planList);
        return new ResponseEntity<>(planResponseDtoList, HttpStatus.OK);
    }

    /**
     * 나의 여정들을 조회
     * @param auth
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/me"})
    public ResponseEntity<?> getPlanMe(Authentication auth) throws Exception {
        long id = userService.extractUserId(auth);
        User user = userService.getUserInfo(id);
        List<Plan> planList = planService.getMyPlans(user);
        List<PlanDto.PlanResponseDto> planResponseDtoList = planMapper.planListToPlanResponseDtoList(planList);
        return new ResponseEntity<>(planResponseDtoList, HttpStatus.OK);
    }

    /**
     * 나의 여정 등록
     * @param auth
     * @param planPostDto
     * @return
     * @throws Exception
     */
    @PostMapping(value = {"/", ""})
    public ResponseEntity<?> postPlan(Authentication auth, @RequestBody PlanDto.PlanPostDto planPostDto) throws Exception {
        long userId = userService.extractUserId(auth);
        Plan plan = planMapper.planPostDtoToPlan(planPostDto);
        plan.setUser(userService.getUserInfo(userId));
        planService.savePlan(plan, planPostDto.getSites());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = {"/", ""})
    public ResponseEntity<?> patchPlan(Authentication auth) throws Exception {
        long id = userService.extractUserId(auth);
        System.out.printf(String.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
