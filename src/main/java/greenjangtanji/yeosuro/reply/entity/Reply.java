package greenjangtanji.yeosuro.reply.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import greenjangtanji.yeosuro.config.Timestamped;
import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.user.entity.Member;
import greenjangtanji.yeosuro.reply.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "reply")
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private long LikesCount;


    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;


    @ManyToOne
    @JoinColumn(name = "feed_id")
    @JsonBackReference
    private Feed feed;


    public static Reply createReply (ReplyRequestDto.Post requestDto, Member member, Feed feed){
        Reply reply = new Reply();
        reply.content = requestDto.getContent();
        reply.feed = feed;
        reply.member = member;
        return reply;
    }

    public void updateReply (String content){
        this.content = content;
    }
}
