package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "groups_seq", allocationSize = 1)
public class Group
        extends GenericModel{

    @Column(name = "responsible", nullable = false)
    private String responsible;
//    @ManyToOne
//    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_GROUP_ROLE_INFO"))
//    private Role role;
    @OneToMany(mappedBy = "group")
    private Set<User> user;
}
