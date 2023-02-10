package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
