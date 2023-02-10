package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "worker_group")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "workers_seq", allocationSize = 1)
public class WorkerGroup
        extends GenericModel{

    @Column(name = "responsible", nullable = false)
    private String responsible;
    @ManyToOne
    @JoinColumn(name = "role", foreignKey = @ForeignKey(name = "FK_WORKER_GROUP_INFO_ROLE"))
    private Role role;
}
