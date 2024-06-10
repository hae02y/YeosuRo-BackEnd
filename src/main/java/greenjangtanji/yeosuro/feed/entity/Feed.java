package greenjangtanji.yeosuro.feed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import greenjangtanji.yeosuro.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "feed")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(nullable = false)
    private String title;

    //@Column(nullable = false)
    private String content;

   // @Column(nullable = false)
    private int view = 1;

   // @Column(nullable = false)
    private String imageUrl;

    private long boardLikesCount;

//    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)
//    private FeedCategory category;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private Member member;


//    public enum FeedCategory{
//
//    }

}
