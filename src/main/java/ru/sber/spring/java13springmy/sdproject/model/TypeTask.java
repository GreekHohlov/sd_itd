package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "type_task")
public class TypeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_type", nullable = false)
    private String nameType;

    @ManyToOne
    @JoinColumn(name = "sla", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SLA"))
    private SLA sla;
    @OneToMany(mappedBy = "typeTask")
    private Set<Task> task;
}
