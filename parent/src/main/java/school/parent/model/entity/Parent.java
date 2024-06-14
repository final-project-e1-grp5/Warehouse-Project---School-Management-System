package school.parent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import school.parent.model.mapper.IntegerListConverter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @Column(name = "child_ids")
    @Convert(converter = IntegerListConverter.class)
    private List<Integer> childIds;

}