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
    @OneToMany(mappedBy = "role")
    private Set<Group> groups;
}
