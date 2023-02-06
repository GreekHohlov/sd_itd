package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoty_second")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "categoty_second_seq", allocationSize = 1)
public class CategorySecond extends GenericModel {
    @Column(name = "name_categoty_second", nullable = false)
    private String nameCategotySecond;
}
