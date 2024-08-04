package greenjangtanji.yeosuro.site.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/sites")
public class SiteController {

    @GetMapping("")
    public ResponseEntity<> getMySites() {

        return (ResponseEntity) ResponseEntity.ok();
    }
}
