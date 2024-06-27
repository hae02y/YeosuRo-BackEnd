package greenjangtanji.yeosuro.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import greenjangtanji.yeosuro.config.Timestamped;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.user.entity.Member;
import greenjangtanji.yeosuro.reply.entity.Reply;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int view = 1;

    @Column(nullable = false)
    private String imageUrl;

    private long LikesCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;

    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reply> replies = new ArrayList<>();

    public static Feed createFeed (FeedRequestDto.Post requestDto, Member member){
        Feed feed = new Feed();
        feed.title = requestDto.getTitle();
        feed.content = requestDto.getContent();
        feed.imageUrl = requestDto.getImageUrl();
        feed.member = member;
        return feed;
    }


    public void updateTitle (String title){
        this.title= title;
    }
    public void updateContent (String content){
        this.content = content;
    }

    public void updateImage (String ImageUrl){
        this.imageUrl = imageUrl;
    }

}
