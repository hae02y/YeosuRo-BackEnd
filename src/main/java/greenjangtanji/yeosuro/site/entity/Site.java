package greenjangtanji.yeosuro.site.entity;

import greenjangtanji.yeosuro.plan.entity.Plan;
import jakarta.persistence.*;

@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @Column
    private String memo;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
