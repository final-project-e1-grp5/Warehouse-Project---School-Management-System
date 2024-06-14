package school.classes.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "class_name")
    private String className;

    @Column(name = "class_year")
    private Integer classYear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 100) @NotNull String getClassName() {
        return className;
    }

    public void setClassName(@Size(max = 100) @NotNull String className) {
        this.className = className;
    }

    public Integer getClassYear() {
        return classYear;
    }

    public void setClassYear(Integer classYear) {
        this.classYear = classYear;
    }
}