package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "users_seq", allocationSize = 1)
public class User
        extends GenericModel {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "FK_USER_INFO_LOCATION"))
    private Location location;

    @ManyToOne
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "FK_USER_GROUP_INFO"))
    private Group groups;
    @OneToMany(mappedBy = "user")
    private Set<Task> tasks;
}
