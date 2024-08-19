package greenjangtanji.yeosuro.notice.dto;

import greenjangtanji.yeosuro.notice.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class NoticeResponseDto {

    @Getter
    @NoArgsConstructor
    public static class DetailNotice {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;

        public DetailNotice(Notice notice){
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.content = notice.getContent();
            this.createAt = notice.getCreateAt();
            this.modifiedAt = notice.getModifiedAt();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class BriefNotice {
        private Long id;
        private String title;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;

        public BriefNotice (Notice notice){
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.createAt = notice.getCreateAt();
            this.modifiedAt = notice.getModifiedAt();
        }
    }
}
