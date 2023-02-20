//package ru.sber.spring.java13springmy.sdproject.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "workers")
//@Getter
//@Setter
//@NoArgsConstructor
//@SequenceGenerator(name = "default_gen", sequenceName = "workers_seq", allocationSize = 1)
//public class Worker
//        extends GenericModel {
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "middle_name")
//    private String middleName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "login", nullable = false)
//    private String login;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @ManyToOne
//    @JoinColumn(name = "worker_group_id", foreignKey = @ForeignKey(name = "FK_WORKER_INFO_GROUP"))
//    private Group workerGroup;
//}
