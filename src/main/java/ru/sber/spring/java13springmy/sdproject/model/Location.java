package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "workers_seq", allocationSize = 1)
public class Location
        extends GenericModel {

    @Column(name = "name_lcation")
    private String nameLocation;
}
