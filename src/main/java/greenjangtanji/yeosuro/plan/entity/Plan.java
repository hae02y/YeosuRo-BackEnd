package greenjangtanji.yeosuro.plan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import greenjangtanji.yeosuro.global.config.Timestamped;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "plan")
public class Plan extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column
    private String content;

    @Column
    private String imageUrl;

    @Column(nullable = true)
    private String date;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<Site> sites = new ArrayList<>();

    @Builder
    public Plan(String title, String date, User user, String content, String imageUrl){
        this.title = title;
        this.date = date;
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
    }

}