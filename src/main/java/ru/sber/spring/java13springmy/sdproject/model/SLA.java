package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sla")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "sla_info_seq", allocationSize = 1)
public class SLA extends GenericModel {
    @Column(name = "name_sla", nullable = false)
    private String nameSLA;

    @Column(name = "response_time")
    private Integer responseTime;
    @Column(name = "execution_time")
    private Integer executionTime;
}
