package com.rikkei.managementuser.service;

import com.rikkei.managementuser.model.dto.request.PostRequest;
import com.rikkei.managementuser.model.dto.response.PostResponse;
import com.rikkei.managementuser.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    List<PostResponse> findAll();
    PostResponse saveOrUpdate (PostRequest postRequest);
    Post findById (Long id);
    void delete (Long id);
    Page<PostResponse> getAll (Pageable pageable);
    Page<PostResponse> searchPostByName (Pageable pageable, String name);
}
