package com.example.role;

import com.example.permission.Permission;
import com.example.security.ApplicationUserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role {

    @Id
    @SequenceGenerator(name = "role_id_sequence", sequenceName = "role_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_sequence")
    private Integer id;
    private ApplicationUserRole name;

    // @ManyToMany()
    // @JoinTable(name = "role_permisson", joinColumns = @JoinColumn(name =
    // "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    // private Set<Permission> permissions = new HashSet<>();

    // public Role() {
    // }

    // public Role(Integer id, ApplicationUserRole name, Set<Permission>
    // permissions) {
    // this.id = id;
    // this.name = name;
    // this.permissions = permissions;
    // }

    // public Integer getId() {
    // return this.id;
    // }

    // public void setId(Integer id) {
    // this.id = id;
    // }

    // public ApplicationUserRole getName() {
    // return this.name;
    // }

    // public void setName(ApplicationUserRole name) {
    // this.name = name;
    // }

    // public Set<Permission> getPermissions() {
    // return this.permissions;
    // }

    // public void setPermissions(Set<Permission> permissions) {
    // this.permissions = permissions;
    // }

    // public Role id(Integer id) {
    // setId(id);
    // return this;
    // }

    // public Role name(ApplicationUserRole name) {
    // setName(name);
    // return this;
    // }

    // public Role permissions(Set<Permission> permissions) {
    // setPermissions(permissions);
    // return this;
    // }

    // @Override
    // public boolean equals(Object o) {
    // if (o == this)
    // return true;
    // if (!(o instanceof Role)) {
    // return false;
    // }
    // Role role = (Role) o;
    // return Objects.equals(id, role.id) && Objects.equals(name, role.name)
    // && Objects.equals(permissions, role.permissions);
    // }

    // @Override
    // public int hashCode() {
    // return Objects.hash(id, name, permissions);
    // }

    // @Override
    // public String toString() {
    // return "{" +
    // " id='" + getId() + "'" +
    // ", name='" + getName() + "'" +
    // ", permissions='" + getPermissions() + "'" +
    // "}";
    // }

}
