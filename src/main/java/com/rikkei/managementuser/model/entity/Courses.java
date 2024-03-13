package com.rikkei.managementuser.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Courses")
@Builder
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "Title", nullable = false , unique = true)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "CourseTime")
    private int courseTime;

    @OneToMany(mappedBy = "courses")
    private Set<Class> classes;

    @OneToMany(mappedBy = "course")
    private Set<ModuleCourse> moduleCourses;


//    @Column(name = "StartDate", nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date startDate;
//
//    @Column(name = "EndDate")
//    @Temporal(TemporalType.DATE)
//    private Date endDate;

//    @Column(name = "Status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private CourseStatus status;

//    @ManyToMany(mappedBy = "taughtCourses")
//    private Set<Teacher> instructors;

//    @OneToMany(mappedBy = "course")
//    private Set<CourseSession> courseSessions;

//    @OneToMany(mappedBy = "course")
//    private Set<Enrollment> enrollments;


}




