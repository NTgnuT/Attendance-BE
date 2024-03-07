package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Courses,Long> {
    List<Courses> findAllByTitleContainingOrDescriptionContaining(String title, String description);

}
