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
@Table(name = "work_schedule")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "work_schedule_seq", allocationSize = 1)
public class WorkSchedule extends GenericModel {
    @Column(name = "year")
    private Integer year;
    @Column(name = "month")
    private Integer month;
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @Column(name = "start_event")
    private LocalDate startEvent;
    @Column(name = "end_event")
    private LocalDate endEvent;
    @Column(name = "day_off")
    private Boolean dayOff;
}
