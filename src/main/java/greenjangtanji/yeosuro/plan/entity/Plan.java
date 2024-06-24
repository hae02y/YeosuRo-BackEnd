package greenjangtanji.yeosuro.plan.entity;

import greenjangtanji.yeosuro.config.Timestamped;
import greenjangtanji.yeosuro.member.entity.Member;
import jakarta.persistence.*;


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
    private Member member;

}
