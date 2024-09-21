package greenjangtanji.yeosuro.plan.dto;

import greenjangtanji.yeosuro.site.dto.SiteDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@ToString
public class PlanDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanPostDto {
        private String title;
        private String content;
        private List<String> imageUrls;
        private List<SiteDto.SitePostDto> sites;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanPatchDto {
        private String title;
        private String content;
        private List<String> imageUrls;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanResponseDto {
        private Long userId;
        private List<String> imageUrls;
        private String title;
        private String content;
        private String createAt;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long planId;
        private List<SiteDto.SiteResponseDto> siteList;
    }

    @Getter
    @Setter
    public static class PlanRequestDto {
        private Long userId;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @Setter
    public static class PlanListResponseDto {
        private Long userId;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}

