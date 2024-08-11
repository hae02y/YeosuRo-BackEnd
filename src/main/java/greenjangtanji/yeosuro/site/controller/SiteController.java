package greenjangtanji.yeosuro.site.controller;

import greenjangtanji.yeosuro.plan.service.PlanService;
import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.site.mapper.SiteMapper;
import greenjangtanji.yeosuro.site.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sites")
public class SiteController {

    private final SiteService siteService;
    private final PlanService planService;
    private final SiteMapper siteMapper;

    @Autowired
    public SiteController(SiteService siteService, PlanService planService, SiteMapper siteMapper) {
        this.siteService = siteService;
        this.planService = planService;
        this.siteMapper = siteMapper;
    }

    //사이트 개별 조회
    @GetMapping(value = {"/{siteId}"})
    public ResponseEntity<?> getSite(@PathVariable(value = "siteId") Long id) {
        Site site = siteService.getSite(id);
        SiteDto.SiteResponseDtoNoDate siteResponseDtoNoDate = siteMapper.siteToSiteResponseDtoNoDate(site);
        return new ResponseEntity<>(siteResponseDtoNoDate, HttpStatus.OK);
    }

    //모든 사이트 조회
    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getSites(){
        List<Site> allSites = siteService.getAllSites();
        List<SiteDto.SiteResponseDtoNoDate> siteResponseDtoNoDateList = siteMapper.siteToSiteResponseDtoNoDateList(allSites);
        return new ResponseEntity<>(siteResponseDtoNoDateList, HttpStatus.OK);
    }

}