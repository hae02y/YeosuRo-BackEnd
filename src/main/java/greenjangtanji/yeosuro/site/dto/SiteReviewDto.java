package greenjangtanji.yeosuro.site.dto;

import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SiteReviewDto {

    @Setter
    @Getter
    public static class SiteReviewPostDto {
        private Long siteId;
        private String contents;
        private List<Image> images;
    }

    @Getter
    @Setter
    public static class SiteReviewResponseDto{
        private Long id;
        private String contents;
    }

    // Site 개별 조회에서 사용 -> 사용자가 장소만 검색하는 경우
    @Getter
    @Setter
    public static class SiteReviewResponseDtoNoDate{
        private Long id;
        private String contents;
    }
}
