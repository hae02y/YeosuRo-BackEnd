package greenjangtanji.yeosuro.site.mapper;

import greenjangtanji.yeosuro.plan.dto.PlanDto;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.entity.Site;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteDto.SiteResponseDto siteToSiteResponseDto(Site site);

    SiteDto.SiteResponseDtoNoDate siteToSiteResponseDtoNoDate(Site site);

    List<SiteDto.SiteResponseDtoNoDate> siteToSiteResponseDtoNoDateList(List<Site> sites);
}
