package greenjangtanji.yeosuro.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.global.config.Timestamped;
import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.reply.entity.Reply;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "feed")
public class Feed extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int view = 1;

    private Long LikesCount = 1L;

    @Enumerated(EnumType.STRING)
    private FeedCategory feedCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "referenceId", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();


    public static Feed createFeed (FeedRequestDto.Post requestDto, User user){
        Feed feed = new Feed();
        feed.title = requestDto.getTitle();
        feed.content = requestDto.getContent();
        feed.feedCategory = FeedCategory.valueOf(requestDto.getFeedCategory().toUpperCase());
        feed.user = user;
        return feed;
    }


    public void updateTitle (String title){
        this.title= title;
    }
    public void updateContent (String content){
        this.content = content;
    }

    public void updateCategory (FeedCategory feedCategory) { this.feedCategory = feedCategory; }
    public int getRepliesCount() {
        return replies.size();
    }

}
