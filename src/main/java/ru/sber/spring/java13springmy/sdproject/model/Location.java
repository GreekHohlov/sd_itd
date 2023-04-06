package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "workers_seq", allocationSize = 1)
public class Location extends GenericModel {

    @Column(name = "name_location")
    private String nameLocation;
    @OneToMany(mappedBy = "location")
    private Set<User> users;
}
