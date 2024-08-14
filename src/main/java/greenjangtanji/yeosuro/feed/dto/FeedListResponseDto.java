package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.point.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FeedListResponseDto {
    private Long id;
    private String title;
    private List<String> imageUrls;
    private int likesCount;
    private int view;
    private String content;
    private int repliesCount;
    private String feedCategory;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long memberID;
    private String nickname;
    private String profileImageUrl;
    private Tier tier;

    public FeedListResponseDto(Feed feed, List<String> imageUrls, String profileImageUrl) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.imageUrls = imageUrls;
        this.likesCount = feed.getLikeCount();
        this.view = feed.getView();
        this.content = feed.getContent();
        this.repliesCount = feed.getRepliesCount();
        this.feedCategory = String.valueOf(feed.getFeedCategory());
        this.createAt = feed.getCreateAt();
        this.modifiedAt = feed.getModifiedAt();
        this.memberID = feed.getUser().getId();
        this.nickname = feed.getUser().getNickname();
        this.profileImageUrl = profileImageUrl;
        this.tier = feed.getUser().getTier();
    }
}
