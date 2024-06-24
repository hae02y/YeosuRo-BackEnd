package greenjangtanji.yeosuro.plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @GetMapping()
    public ResponseEntity<?> getPlan(@RequestBody(required = false) String body) {


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
