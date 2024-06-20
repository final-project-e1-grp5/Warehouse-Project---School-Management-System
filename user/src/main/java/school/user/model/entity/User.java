package school.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Size(max = 50)
    @NotNull
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;


    @Size(max = 100)
    @NotNull
    @Column(name = "role", nullable = false, length = 100)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public @Size(max = 50) @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@Size(max = 50) @NotNull String username) {
        this.username = username;
    }

    public @Size(max = 100) @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 100) @NotNull String email) {
        this.email = email;
    }

    public @Size(max = 100) @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 100) @NotNull String password) {
        this.password = password;
    }

    public @NotNull Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(@NotNull Boolean enabled) {
        this.enabled = enabled;
    }
}