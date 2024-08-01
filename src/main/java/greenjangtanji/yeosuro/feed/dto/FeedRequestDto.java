package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

public class FeedRequestDto {
    @Getter
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        @Size(max = 50, message = "제목 글자수 제한 초과")
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
