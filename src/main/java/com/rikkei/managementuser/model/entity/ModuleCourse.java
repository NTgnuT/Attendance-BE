package com.rikkei.managementuser.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ModuleName", nullable = false,unique = true)
    @Length(max = 100, min = 6)
    private String moduleName;

    @Column(name = "Time")
    private int time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CourseId")
    private Courses course;


}
