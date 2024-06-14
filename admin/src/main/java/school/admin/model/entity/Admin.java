package school.admin.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Size(max = 50)
    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50)
    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 100)
    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public @Size(max = 100) String getAddress() {
        return address;
    }

    public void setAddress(@Size(max = 100) String address) {
        this.address = address;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}