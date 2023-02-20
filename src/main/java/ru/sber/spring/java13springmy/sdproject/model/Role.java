package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "defaelt_gen", sequenceName = "worker_seq", allocationSize = 1)
public class Role
        extends GenericModel{

    @Column(name = "name_role", nullable = false)
    private String nameRole;
    @ManyToMany
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "FK_ROLE_GROUP"),
            inverseJoinColumns = @JoinColumn(name = "group_id"),
            inverseForeignKey = @ForeignKey(name = "FK_GROUP_ROLE"))
    private Set<Group> group;
}
