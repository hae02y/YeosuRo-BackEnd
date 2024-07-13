package greenjangtanji.yeosuro.plan.entity;

import jakarta.persistence.*;

/**
 * Validation 정의 필요
 */
@Entity
public class PlanReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @Column
    private String contents;

    @Column
    private String image;
}
