package com.store.storebookspring.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;


/**
 * User entity DB
 */
@Data
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @NotEmpty(message = "Please provide a user name")
    private String username;

    @NotEmpty(message = "Please provide your password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user")
    private List<Role> roles;

    /**
     * get id user
     * @return long
     */
    public Long getIdUser() {
        return id_user;
    }

    /**
     * set id user
     * @param idUser long
     */
    public void setIdUser(Long idUser) {
        this.id_user = idUser;
    }

    /**
     * get user_name
     * @return string
     */
    public String getUsername() {
        return username;
    }

    /**
     * set user_name
     * @param username string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get password
     * @return string
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     * @param password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get roles user
     * @return list of roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * set roles
     * @param roles list
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}