package greenjangtanji.yeosuro.site.entity;

import greenjangtanji.yeosuro.image.entity.Image;
import greenjangtanji.yeosuro.plan.entity.PlanReview;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SiteReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @OneToMany(mappedBy = "referenceId", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "plan_review_id")
    private PlanReview planReview;

    @OneToOne
    @JoinColumn(name = "site_id")
    private Site site;


    @Builder
    public SiteReview(String contents, PlanReview planReview, Site site) {
        this.contents = contents;
        this.planReview = planReview;
        this.site = site;
    }
}
