package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInstructorRepository extends JpaRepository<Instructor,Long> {
    Optional<Instructor> findByName(String name);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

}
