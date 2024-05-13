package com.rikkei.managementuser.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Enumerated(EnumType.STRING)
//    @Column(name = "attendance_status")
//    private AttendanceStatus attendanceStatus;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "student_id", nullable = false)
//    private Student student;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "class_id", nullable = false)
//    private Class aClass;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "lesson_id", nullable = false)
//    private Lesson lesson;
    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "scheduleId")
    private Schedule schedule;

    private Date timeAttendance;

    @OneToMany(mappedBy = "attendance")
    private Set<AttendanceDetail> attendanceDetails;
}
