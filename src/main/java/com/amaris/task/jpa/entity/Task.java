package com.amaris.task.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name="DUE_DATE")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID")
    private Employee assignee;

}
