package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.point.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FeedListResponseDto {
    private Long id;
    private String title;
    private String imageUrl;
    private Long likesCount;
    private int repliesCount;
    private String feedCategory;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long memberID;
    private String nickname;
    private String profileImageUrl;
    private Tier tier;

    public FeedListResponseDto (Feed feed){
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.imageUrl = feed.getImageUrl();
        this.likesCount = feed.getLikesCount();
        this.repliesCount = feed.getRepliesCount();
        this.feedCategory = String.valueOf(feed.getFeedCategory());
        this.createAt = feed.getCreateAt();
        this.modifiedAt = feed.getModifiedAt();
        this.memberID = feed.getUser().getId();
        this.nickname = feed.getUser().getNickname();
        this.profileImageUrl= feed.getUser().getProfileImageUrl();
        this.tier= feed.getUser().getTier();
    }

    public FeedListResponseDto (Optional<Feed> feed){
        this.id = feed.get().getId();
        this.title = feed.get().getTitle();
        this.imageUrl = feed.get().getImageUrl();
        this.likesCount = feed.get().getLikesCount();
        this.repliesCount = feed.get().getRepliesCount();
        this.feedCategory = String.valueOf(feed.get().getFeedCategory());
        this.createAt = feed.get().getCreateAt();
        this.modifiedAt = feed.get().getModifiedAt();
        this.memberID = feed.get().getUser().getId();
        this.nickname = feed.get().getUser().getNickname();
        this.profileImageUrl= feed.get().getUser().getProfileImageUrl();
        this.tier= feed.get().getUser().getTier();
    }

}