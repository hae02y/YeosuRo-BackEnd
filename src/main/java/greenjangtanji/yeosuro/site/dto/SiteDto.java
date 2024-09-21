package greenjangtanji.yeosuro.site.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SiteDto {

    @Setter
    @Getter
    public static class SitePostDto {
        private String category;
        private String name;
        private List<String> imageUrls;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
        private LocalDate visitDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Getter
    @Setter
    public static class SiteResponseDto{
        private Long id;
        private String category;
        private String name;
        private List<String> imageUrls;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
        private LocalDate visitDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    // Site 개별 조회에서 사용 -> 사용자가 장소만 검색하는 경우
    @Getter
    @Setter
    public static class SiteResponseDtoNoDate{
        private Long id;
        private String name;
        private List<String> imageUrls;
        private String category;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
    }
}
