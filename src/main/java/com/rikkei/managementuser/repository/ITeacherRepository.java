package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

}
