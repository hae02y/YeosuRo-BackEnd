package greenjangtanji.yeosuro.site.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

public class SiteDto {

    @Setter
    @Getter
    public static class SitePostDto {
        private String category;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
        private Long visitDate;
    }

    @Getter
    @Setter
    public static class SiteResponseDto{
        private Long id;
        private String category;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
        private Long visitDate;
    }

    // Site 개별 조회에서 사용 -> 사용자가 장소만 검색하는 경우
    @Getter
    @Setter
    public static class SiteResponseDtoNoDate{
        private Long id;
        private String category;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
    }
}
