package greenjangtanji.yeosuro.plan.controller;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.service.PlanService;
import greenjangtanji.yeosuro.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/plan")
public class PlanController {


    private final UserService userService;
    private final PlanService planService;

    @Autowired
    public PlanController(UserService userService, PlanService planService){
        this.userService = userService;
        this.planService = planService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getPlan(Authentication auth) throws Exception {

        long id = userService.extractUserId(auth);
        System.out.printf(String.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<?> postPlan(Authentication auth, @RequestBody PlanDto.PlanPatchDto planPostDto) throws Exception {

        long id = userService.extractUserId(auth);


        System.out.printf(String.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PatchMapping(value = {"/", ""})
    public ResponseEntity<?> patchPlan(Authentication auth) throws Exception {
        long id = userService.extractUserId(auth);
        System.out.printf(String.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
