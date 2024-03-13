package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.ModuleCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IModuleCourseRepository extends JpaRepository<ModuleCourse, Long> {
    boolean existsByModuleName(String name);
}
