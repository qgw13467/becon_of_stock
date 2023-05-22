package strategy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Long count;

    private String description;

    @Enumerated(EnumType.STRING)
    private SortType sortType;

    public Indicator(String title) {
        this.title = title;
    }
}
