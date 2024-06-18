package greenjangtanji.yeosuro.member.entity;

import greenjangtanji.yeosuro.feed.entity.Feed;
import greenjangtanji.yeosuro.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reply> replies = new ArrayList<>();
}
