package greenjangtanji.yeosuro.plan.controller;

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

    @Autowired
    public PlanController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getPlan(Authentication auth) throws Exception {

        long id = userService.extractUserId(auth);

        System.out.printf(String.valueOf(id));

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/hi")
    public ResponseEntity<?> getPlan2(@RequestParam(required = false) String body) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
