package greenjangtanji.yeosuro.site.entity;

import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.plan.entity.Plan;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private String name;

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

    @OneToMany(mappedBy = "referenceId", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Site(String category, String name, String memo, String latitude, String longitude, String address, LocalDate visitDate, LocalTime startTime, LocalTime endTime, Plan plan) {
        this.category = category;
        this.name = name;
        this.memo = memo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.visitDate = visitDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.plan = plan;
    }

}
