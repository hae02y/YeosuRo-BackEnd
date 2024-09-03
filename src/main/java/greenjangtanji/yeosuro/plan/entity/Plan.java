package greenjangtanji.yeosuro.plan.entity;

import greenjangtanji.yeosuro.global.config.Timestamped;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "plan")
public class Plan extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<Site> sites = new ArrayList<>();

    @Builder
    public Plan(String title, User user, String content, LocalDate startDate, LocalDate endDate, List<Site> sites){
        this.title = title;
        this.user = user;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sites = sites;
    }

}