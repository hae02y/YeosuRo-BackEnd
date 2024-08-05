package greenjangtanji.yeosuro.site.mapper;

import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.entity.Site;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteDto.SiteResponseDto siteToSiteResponseDto(Site site);
}
