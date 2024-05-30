package com.rikkei.managementuser.service.serviceImpl;

import com.rikkei.managementuser.model.dto.request.PostRequest;
import com.rikkei.managementuser.model.dto.response.PostResponse;
import com.rikkei.managementuser.model.entity.Post;
import com.rikkei.managementuser.repository.PostRepository;
import com.rikkei.managementuser.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final UploadService uploadService;

    @Override
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(post -> PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .content(post.getContent()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public PostResponse saveOrUpdate(PostRequest postRequest) {
        Post post;
        if (postRequest.getId() == null) {
            if (postRepository.existsByName(postRequest.getName())) {
                throw new NoSuchElementException("Tiêu đề này đã được sử dụng rồi");
            }
            post = new Post();
            post.setName(postRequest.getName());

            String fileName = uploadService.uploadContent(postRequest.getContent());
            post.setContent(fileName);
        } else {
            post = postRepository.findById(postRequest.getId()).orElseThrow(() -> new NoSuchElementException("Không tìm thấy bài viết này"));
            String fileName;
            if (postRequest.getContent() != null && !postRequest.getContent().isEmpty()) {
                fileName = uploadService.uploadContent(postRequest.getContent());
            } else {
                fileName = post.getContent();
            }

            if (!post.getName().equals(postRequest.getName())) {
                if (postRepository.existsByName(postRequest.getName())) {
                    throw new NoSuchElementException("Tiêu đề này đã được sử dụng rồi ");
                }
                post.setName(postRequest.getName());
            }

            post.setContent(fileName);
        }

        postRepository.save(post);
        return PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .content(post.getContent())
                .build();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(()->new NoSuchElementException("Không tìm thấy bài viết này"));
    }

    @Override
    public void delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
    }

    @Override
    public Page<PostResponse> getAll(Pageable pageable) {
        Page<Post> postPage =postRepository.findAll(pageable);
        return postPage.map(post -> PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .content(post.getContent())
                .build());
    }

    @Override
    public Page<PostResponse> searchPostByName(Pageable pageable, String name) {
        Page<Post> postPage = postRepository.findAllByNameContainingIgnoreCase(pageable, name);
        return postPage.map(post -> PostResponse.builder()
                .id(post.getId())
                .name(post.getName())
                .content(post.getContent())
                .build());
    }
}
