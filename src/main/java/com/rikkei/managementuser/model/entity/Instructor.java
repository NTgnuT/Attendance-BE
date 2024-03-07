package com.rikkei.managementuser.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    // Constructors, getters and setters

    @ManyToMany
    @JoinTable(
            name = "CoursesInstructors",
            joinColumns = @JoinColumn(name = "InstructorID"),
            inverseJoinColumns = @JoinColumn(name = "CourseID")
    )
    private Set<Courses> taughtCourses;


}