package com.store.storebookspring.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Role entity DB
 */
@Data
@Entity
@Table(name="role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @NotEmpty
    private String name;

    /**
     * get name role
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * set name role
     * @param name string
     */
    public void setName(String name) {
        this.name = name;
    }
}