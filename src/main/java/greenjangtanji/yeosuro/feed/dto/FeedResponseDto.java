package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.Feed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FeedResponseDto {

    private Long id;
    private Long memberID;
    private String title;
    private String content;
    private String imageUrl;
    private int view;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public FeedResponseDto (Feed feed){
        this.id = feed.getId();
        this.memberID = feed.getMember().getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.imageUrl = feed.getImageUrl();
        this.view = feed.getView();
        this.createAt = feed.getCreateAt();
        this.modifiedAt = feed.getModifiedAt();
    }

}
