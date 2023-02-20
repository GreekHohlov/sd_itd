package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "tasks_seq", allocationSize = 1)
public class Task extends GenericModel {
    @Column(name = "name_task", nullable = false)
    private String bookTitle;
    @ManyToOne
    @JoinColumn(name = "type_task", foreignKey = @ForeignKey(name = "FK_TASK_INFO_TYPE_TASK"))
    private TypeTask typeTask;

    @Column(name = "priority", nullable = false)
    @Enumerated
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category", foreignKey = @ForeignKey(name = "FK_TASK_INFO_CATEGORY_FIRST"))
    private Category category;

    @Column(name = "description", nullable = false)
    private String discription;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "attachment_id", foreignKey = @ForeignKey(name = "FK_ATTACHMENT_INF"))
    private Attachments attachments;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_TASK_INFO_USER"))
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "worker_id", foreignKey = @ForeignKey(name = "FK_TASK_INFO_WORKER"))
//    private Worker worker;

    @Column(name = "status", nullable = false)
    @Enumerated
    private StatusTask statusTask;
}
