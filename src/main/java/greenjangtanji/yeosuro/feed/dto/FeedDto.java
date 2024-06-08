package greenjangtanji.yeosuro.feed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;


public class FeedDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {

        private Long memberID;
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        private String imageUrl;

    }
}
