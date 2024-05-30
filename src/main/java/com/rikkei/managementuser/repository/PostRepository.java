package com.rikkei.managementuser.repository;

import com.rikkei.managementuser.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Boolean existsByName (String name);
    Page<Post> findAllByNameContainingIgnoreCase (Pageable pageable, String name);
}
