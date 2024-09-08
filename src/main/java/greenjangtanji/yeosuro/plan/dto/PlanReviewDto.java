package greenjangtanji.yeosuro.plan.dto;

import greenjangtanji.yeosuro.plan.entity.KeywordType;
import greenjangtanji.yeosuro.plan.entity.Plan;
import greenjangtanji.yeosuro.site.dto.SiteDto;
import greenjangtanji.yeosuro.site.dto.SiteReviewDto;
import greenjangtanji.yeosuro.site.entity.SiteReview;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
public class PlanReviewDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanReviewPostDto {

        private String contents;
        private String planTerm; //일정 : 당일치기 / 1박2일 / 2박3일 / 3박4일 / 4일이상
        private String difficulty; //난이도 : 상 중 하
        private String companion; //동반자 : 부모 가족 형제 등등
        private String fee; //비용 : 50000원 ~ 100000원...
        private String keywordType;
        private List<SiteReviewDto.SiteReviewPostDto> siteReviews;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanReviewPatchDto {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlanReviewResponseDto {
        private Long userId;
        private String content;
        private List<SiteReviewDto.SiteReviewResponseDto> siteReviewList;
    }

    @Getter
    @Setter
    public static class PlanReviewRequestDto {
        private Long userId;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @Setter
    public static class PlanReviewListResponseDto {
        private Long userId;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
