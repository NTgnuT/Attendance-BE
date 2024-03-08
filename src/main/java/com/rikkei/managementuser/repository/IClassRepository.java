package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.dto.response.ClassResponse;
import com.rikkei.managementuser.model.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClassRepository extends JpaRepository<Class,Long> {
    boolean existsByName(String name);
    List<Class> findAllByNameContainingOrInstructor_NameContaining(String name,String name1);
    List<Class> findAllByNameContaining(String name);
}
