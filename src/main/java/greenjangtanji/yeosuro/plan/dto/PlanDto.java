package greenjangtanji.yeosuro.plan.dto;

import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class PlanDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanPostDto {
        private String title;
        private String content;
        private String imageUrl;
        private String date;
        private List<Site> siteList;
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
        private String date;
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

