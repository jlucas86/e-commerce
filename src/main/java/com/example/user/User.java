package com.example.user;

import com.example.security.ApplicationUserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity

public class User {

    @Id
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Integer id;

    private String email;
    private String username;
    private String password;
    private ApplicationUserRole Role;

    public User() {
    }

    public User(Integer id, String email, String username, String password, ApplicationUserRole Role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.Role = Role;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ApplicationUserRole getRole() {
        return this.Role;
    }

    public void setRole(ApplicationUserRole Role) {
        this.Role = Role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(username, user.username) && Objects.equals(password, user.password)
                && Objects.equals(Role, user.Role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, Role);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", email='" + getEmail() + "'" +
                ", username='" + getUsername() + "'" +
                ", password='" + getPassword() + "'" +
                ", Role='" + getRole() + "'" +
                "}";
    }

}
