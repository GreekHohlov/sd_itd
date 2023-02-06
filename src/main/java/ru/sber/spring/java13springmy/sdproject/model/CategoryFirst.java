package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoty_first")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "categoty_first_seq", allocationSize = 1)
public class CategoryFirst extends GenericModel {
    @Column(name = "name_categoty_first", nullable = false)
    private String nameCategotyFirst;
}
