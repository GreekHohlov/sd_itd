package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "calendar")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "calendar_seq", allocationSize = 1)
public class Calendar extends GenericModel {
    @Column(name = "name_event")
    private String nameEvent;
    @Column(name = "start_event")
    private LocalDate startEvent;
    @Column(name = "end_event")
    private LocalDate endEvent;
}
