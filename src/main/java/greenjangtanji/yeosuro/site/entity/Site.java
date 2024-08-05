package greenjangtanji.yeosuro.site.entity;

import greenjangtanji.yeosuro.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.DoubleStream;

@Entity
@Getter
@NoArgsConstructor
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

    @Column
    private Long visitDate;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;


    @Builder
    public Site(String category, String memo, String latitude, String longitude, String address, Long visitDate, Plan plan) {
        this.category = category;
        this.memo = memo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.visitDate = visitDate;
        this.plan = plan;
    }

}
