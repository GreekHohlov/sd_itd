package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "defaelt_gen", sequenceName = "worker_seq", allocationSize = 1)
public class Role
        extends GenericModel{

    @Column(name = "name_role", nullable = false)
    private String nameRole;
    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "role")
//    private Set<Group> groups;
}
