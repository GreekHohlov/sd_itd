package ru.sber.spring.java13springmy.sdproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_generator", sequenceName = "attachments_seq", allocationSize = 1)
public class Attachments extends GenericModel{
    @Column(name = "attachment_path")
    private String attachmentPath;
    @OneToMany(mappedBy = "attachments")
    private Set<Task> tasks;
}
