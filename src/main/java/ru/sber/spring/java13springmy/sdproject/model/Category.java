package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "category_seq", allocationSize = 1)
public class Category extends GenericModel {
    @Column(name = "name_category", nullable = false)
    private String nameCategory;
    @OneToMany(mappedBy = "category")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "parent_category_id", foreignKey = @ForeignKey(name = "FK_CATEGORY_ID"))
    private Category parentCategoryId;

    @OneToMany(mappedBy = "parentCategoryId")
    private Set<Category> subCategory;
}
