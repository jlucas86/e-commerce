package com.example.permission;

import com.example.security.ApplicationUserPermission;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
public class Permission {

    @Id
    @SequenceGenerator(name = "seller_id_sequence", sequenceName = "seller_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_id_sequence")
    private Integer id;
    private ApplicationUserPermission permission;

    public Permission() {
    }

    public Permission(Integer id, ApplicationUserPermission permission) {
        this.id = id;
        this.permission = permission;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ApplicationUserPermission getPermission() {
        return this.permission;
    }

    public void setPermission(ApplicationUserPermission permission) {
        this.permission = permission;
    }

    public Permission id(Integer id) {
        setId(id);
        return this;
    }

    public Permission permission(ApplicationUserPermission permission) {
        setPermission(permission);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission permission = (Permission) o;
        return Objects.equals(id, permission.id) && Objects.equals(permission, permission.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permission);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", permission='" + getPermission() + "'" +
                "}";
    }
}
