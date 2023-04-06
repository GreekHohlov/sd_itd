package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "type_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "task_type_seq", allocationSize = 1)
public class TypeTask extends GenericModel {

    @Column(name = "name_type", nullable = false)
    private String nameType;

    @ManyToOne
    @JoinColumn(name = "sla", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SLA"))
    private SLA sla;
    @OneToMany(mappedBy = "typeTask")
    private Set<Task> tasks;
}
