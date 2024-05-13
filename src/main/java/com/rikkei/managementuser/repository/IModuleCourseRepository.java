package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.ModuleCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IModuleCourseRepository extends JpaRepository<ModuleCourse, Long> {
    boolean existsByModuleName(String name);
    @Query("SELECT mc from ModuleCourse mc where mc.course.id = :courseId")
    List<ModuleCourse> findAllByCourseId(Long courseId);
}
