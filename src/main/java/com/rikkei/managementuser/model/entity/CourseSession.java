package com.rikkei.managementuser.model.entity;

import com.rikkei.managementuser.model.entity.Courses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CourseSessions")
public class CourseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseSessionId;

    @ManyToOne
    @JoinColumn(name = "CourseID", nullable = false)
    private Courses course;

    @Column(name = "SessionDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date sessionDate;

    @Column(name = "SessionTime", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date sessionTime;

    @Column(name = "Topic")
    private String topic;


}