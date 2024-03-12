package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
    boolean existsByPhoneNumber(String number);
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);
    Optional<Student> findByPhoneNumber(String phone);

}
