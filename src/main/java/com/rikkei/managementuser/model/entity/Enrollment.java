package com.rikkei.managementuser.model.entity;

import com.rikkei.managementuser.model.entity.Courses;
import com.rikkei.managementuser.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Enrollments")
public class Enrollment {

    @Id
    @ManyToOne
    @JoinColumn(name = "StudentID", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    private Courses course;

    @Column(name = "EnrollmentDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    @Column(name = "Score")
    private Float score;

    @Column(name = "Feedback")
    private String feedback;
}