package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "groups_seq", allocationSize = 1)
public class Group
        extends GenericModel{

    @Column(name = "responsible", nullable = false)
    private String responsible;
    @ManyToMany
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn(name = "group_id"),
            foreignKey = @ForeignKey(name = "FK_GROUP_ROLE"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            inverseForeignKey = @ForeignKey(name = "FK_ROLE_GROUP")
    )
    private Set<Role> role;
    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            foreignKey = @ForeignKey(name = "FK_GROUP_USER"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            inverseForeignKey = @ForeignKey(name = "FK_USER_GROUP"))
    private Set<User> users;
}
