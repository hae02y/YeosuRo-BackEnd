package greenjangtanji.yeosuro.feed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int view;

}
