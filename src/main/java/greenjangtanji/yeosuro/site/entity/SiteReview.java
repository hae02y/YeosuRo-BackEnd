package greenjangtanji.yeosuro.site.entity;

import jakarta.persistence.*;

@Entity
public class SiteReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;
}
