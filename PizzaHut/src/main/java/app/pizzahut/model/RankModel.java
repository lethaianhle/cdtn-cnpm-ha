package app.pizzahut.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ranks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RankModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RankModel rankModel = (RankModel) o;
        return id != null && Objects.equals(id, rankModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
