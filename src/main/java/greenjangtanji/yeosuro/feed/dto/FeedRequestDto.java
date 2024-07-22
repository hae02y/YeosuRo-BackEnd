package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

public class FeedRequestDto {
    @Getter
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private String imageUrl;

        @NotBlank
        private String feedCategory;

    }


    @Getter
    @NoArgsConstructor
    public static class Patch {

        private String title;

        private String content;

        private String imageUrl;

        private String feedCategory;

    }


}
