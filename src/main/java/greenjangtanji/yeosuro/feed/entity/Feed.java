package greenjangtanji.yeosuro.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import greenjangtanji.yeosuro.config.Timestamped;
import greenjangtanji.yeosuro.feed.dto.FeedRequestDto;
import greenjangtanji.yeosuro.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

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

    private long boardLikesCount;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;


    public static Feed createFeed (FeedRequestDto.Post requestDto){
        Feed feed = new Feed();
        feed.id = requestDto.getMemberID();
        feed.title = requestDto.getTitle();
        feed.content = requestDto.getContent();
        feed.imageUrl = requestDto.getImageUrl();
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
