package greenjangtanji.yeosuro.member.entity;

import greenjangtanji.yeosuro.feed.entity.Feed;
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


    @OneToMany(mappedBy = "member")
    private List<Feed> feeds = new ArrayList<>();
}
