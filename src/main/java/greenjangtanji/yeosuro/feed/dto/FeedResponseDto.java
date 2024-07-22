package greenjangtanji.yeosuro.feed.dto;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.feed.entity.FeedCategory;
import greenjangtanji.yeosuro.point.entity.Tier;
import greenjangtanji.yeosuro.reply.dto.ReplyResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FeedResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private int view;
    private Long likesCount;
    private int repliesCount;
    private String feedCategory;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long memberID;
    private String nickname;
    private String profileImageUrl;
    private Tier tier;
    private List<ReplyResponseDto> replies;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
        this.imageUrl = feed.getImageUrl();
        this.view = feed.getView();
        this.likesCount = feed.getLikesCount();
        this.repliesCount = feed.getRepliesCount();
        this.feedCategory = String.valueOf(feed.getFeedCategory());
        this.createAt = feed.getCreateAt();
        this.modifiedAt = feed.getModifiedAt();
        this.memberID = feed.getUser().getId();
        this.nickname = feed.getUser().getNickname();
        this.profileImageUrl = feed.getUser().getProfileImageUrl();
        this.tier = feed.getUser().getTier();
        this.replies = feed.getReplies().stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
    }

}

