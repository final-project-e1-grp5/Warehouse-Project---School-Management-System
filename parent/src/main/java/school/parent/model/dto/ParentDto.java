package school.parent.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class ParentDto {
    private String id;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private List<String> childrenIds;

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

    public List<String> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<String> childIds) {
        this.childrenIds = childIds;
    }
}
