package greenjangtanji.yeosuro.plan.dto;

import greenjangtanji.yeosuro.site.dto.SiteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class PlanDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanPostDto {
        private String title;
        private String content;
        private String imageUrl;
        private List<SiteDto.SitePostDto> sites;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanPatchDto {
        private String title;
        private String content;
        private String imageUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanResponseDto {
        private Long userId;
        private String title;
        private String content;
        private String imageUrl;
        private String createAt;
        private List<SiteDto.SiteResponseDto> siteList;
    }

    @Getter
    @Setter
    public static class PlanRequestDto {
        private Long userId;
        private String title;
        private String content;
        private String imageUrl;
    }

    @Getter
    @Setter
    public static class PlanListResponseDto {
        private Long userId;
        private String title;
        private String content;
        private String imageUrl;
    }
}

