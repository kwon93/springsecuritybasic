package com.easybytes.springsecuritybasic.model;

import com.easybytes.springsecuritybasic.model.authenum.Auth;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "customer_id")
    private int id;

    private String name;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    private String role;

    @Column(name = "created_at")
    private String createdAt;

    @JsonIgnore
    @OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
    private Set<Authority> authorities;
}
