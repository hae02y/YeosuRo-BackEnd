package greenjangtanji.yeosuro.plan.entity;

import greenjangtanji.yeosuro.site.entity.SiteReview;
import jakarta.persistence.*;

import java.util.List;

/**
 * Validation 정의 필요
 */
@Entity
public class PlanReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @Column
    private String planTerm; //일정 : 당일치기 / 1박2일 / 2박3일 / 3박4일 / 4일이상

    @Column
    private String difficulty; //난이도 : 상 중 하

    @Column
    private String companion; //동반자 : 부모 가족 형제 등등

    @Column
    private String fee; //비용 : 50000원 ~ 100000원...

    @OneToOne
    private Plan plan;

    @OneToMany
    private List<SiteReview> siteReview;

    //TODO : 키워드 작성 필요

    //TODO : 이미지 매칭
}
