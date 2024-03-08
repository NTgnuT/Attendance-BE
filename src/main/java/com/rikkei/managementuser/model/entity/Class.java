package com.rikkei.managementuser.model.entity;

import com.rikkei.managementuser.validator.ClassUnique;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ClassName")
    private String name;

    private int maxStudent;

    @ManyToOne
    @JoinColumn(name = "instructorName")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "CourseId")
    private Courses courses;


}
