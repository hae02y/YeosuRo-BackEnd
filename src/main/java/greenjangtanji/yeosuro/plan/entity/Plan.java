package greenjangtanji.yeosuro.plan.entity;

import greenjangtanji.yeosuro.global.config.Timestamped;
import greenjangtanji.yeosuro.site.entity.Site;
import greenjangtanji.yeosuro.user.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "plan")
public class Plan extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String date;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "plan")
    private List<Site> sites = new ArrayList<>();

}
