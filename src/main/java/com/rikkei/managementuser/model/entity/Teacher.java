package com.rikkei.managementuser.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Teacher")
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "PhoneNumber",unique = true)
    private String phoneNumber;

    @Column(name = "Address")
    private String address;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "DateOfBirth")
    private Date dob;

    @OneToMany(mappedBy = "teacher")
    private Set<Schedule> schedules;

//    @ManyToMany
//    @JoinTable(
//            name = "CoursesInstructors",
//            joinColumns = @JoinColumn(name = "InstructorID"),
//            inverseJoinColumns = @JoinColumn(name = "CourseID")
//    )
//    private Set<Courses> taughtCourses;

//    @OneToMany(mappedBy = "instructor")
//    private Set<Class> classes;


}