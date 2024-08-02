package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Collections;
import java.util.List;

public class FeedRequestDto {
    @Getter
    @NoArgsConstructor
    public static class Post {

        @NotBlank
        @Size(max = 50, message = "제목 글자수 제한 초과")
        private String title;

        @NotBlank
        private String content;

        // 이미지 URL 리스트의 크기를 5개로 제한
        @Size(max = 5, message = "이미지는 최대 5개까지 업로드할 수 있습니다.")
        private List<String> imageUrls;

        @NotBlank
        private String feedCategory;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setFeedCategory(String feedCategory) {
            this.feedCategory = feedCategory;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrls = Collections.singletonList(imageUrl);
        }

    }


    @Getter
    @NoArgsConstructor
    public static class Patch {

        private String title;

        private String content;

        private List<String> imageUrls;

        private String feedCategory;

    }


}
