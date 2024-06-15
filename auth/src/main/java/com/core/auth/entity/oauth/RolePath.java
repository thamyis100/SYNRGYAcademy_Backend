package com.core.auth.entity.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "oauth_role_path")
public class RolePath implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    private String pattern;

    private String method;

    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private Role role;
}
