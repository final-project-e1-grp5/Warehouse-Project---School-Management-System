package school.parent.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "parents")
public class Parent {
    @Id
    @Column(name = "user_id", nullable = false)
    private String id;

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

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;

    public Parent() {
        this.children = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public @Size(max = 50) @NotNull String getFirstName() {
        return firstName;
    }

    public void setFirstName(@Size(max = 50) @NotNull String firstName) {
        this.firstName = firstName;
    }

    public @Size(max = 50) @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(max = 50) @NotNull String lastName) {
        this.lastName = lastName;
    }

    public @NotNull Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}