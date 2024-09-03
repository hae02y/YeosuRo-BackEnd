package greenjangtanji.yeosuro.site.entity;

import greenjangtanji.yeosuro.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate visitDate;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;


    @Builder
    public Site(String category, String memo, String latitude, String longitude, String address, LocalDate visitDate, LocalTime startTime, LocalTime endTime, Plan plan) {
        this.category = category;
        this.memo = memo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.visitDate = visitDate;
        this.startTime = startTime;
        System.out.println(visitDate);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(this.startTime);
        this.endTime = endTime;
        this.plan = plan;
    }

}
