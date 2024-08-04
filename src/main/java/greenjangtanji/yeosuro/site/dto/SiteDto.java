package greenjangtanji.yeosuro.site.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SiteDto {

    @Setter
    @Getter
    class SitePostDto {
        private String category;
        @Column
        private String memo;

        @Column
        private String latitude;

        @Column
        private String longitude;

        @Column
        private String address;
    }
}
