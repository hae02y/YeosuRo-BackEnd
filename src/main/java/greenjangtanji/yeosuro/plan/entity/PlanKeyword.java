package greenjangtanji.yeosuro.plan.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PlanKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Keyword keyword;

    public PlanKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
