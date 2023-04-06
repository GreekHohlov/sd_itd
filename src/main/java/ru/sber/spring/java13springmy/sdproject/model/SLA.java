package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "sla")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "sla_info_seq", allocationSize = 1)
public class SLA extends GenericModel {
    @Column(name = "name_sla", nullable = false)
    private String nameSLA;

    @Column(name = "response_time")
    private Integer responseTime;
    @Column(name = "execution_time")
    private Integer executionTime;
    @OneToMany(mappedBy = "sla")
    private Set<TypeTask> typeTask;
}
