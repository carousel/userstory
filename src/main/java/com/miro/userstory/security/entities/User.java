package com.miro.userstory.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miro.userstory.entities.WorkExperience;
import com.miro.userstory.security.beans.UserBean;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Security User Entity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"id"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
        = @JoinColumn(name = "user_id",
        referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",
            referencedColumnName = "id"))

    private List<Role> roles;

    @Column(name = "username", length = 250, nullable = false)
    private String username;

    @Column(name = "first_name", length = 250, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 250, nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "description", length = 250, nullable = true)
    private String description;

    @ElementCollection
    private Set<WorkExperience> workExperience = new HashSet<>();
}

