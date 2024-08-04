package greenjangtanji.yeosuro.site.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SiteDto {

    @Setter
    @Getter
    @ToString
    public static class SitePostDto {
        private String category;
        private String memo;
        private String latitude;
        private String longitude;
        private String address;
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
    }
}
