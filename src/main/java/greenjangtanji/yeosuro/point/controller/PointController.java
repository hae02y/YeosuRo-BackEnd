package greenjangtanji.yeosuro.point.controller;

import greenjangtanji.yeosuro.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
@Validated
public class PointController {

    private final PointService pointService;

    //회원 포인트 조회
    @GetMapping
    public ResponseEntity getUserPoints (){
        return null;
    }

}
