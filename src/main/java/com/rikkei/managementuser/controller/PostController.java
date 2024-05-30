package com.rikkei.managementuser.controller;

import com.rikkei.managementuser.exception.ScheduleException;
import com.rikkei.managementuser.model.dto.request.PostRequest;
import com.rikkei.managementuser.model.dto.response.PostResponse;
import com.rikkei.managementuser.model.dto.response.ResponseAPI;
import com.rikkei.managementuser.model.entity.Post;
import com.rikkei.managementuser.service.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management/api/post")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;
//    @GetMapping("")
//    public ResponseEntity<?> findAll () {
//        return ResponseEntity.status(200).body(postService.findAll());
//    }

    @GetMapping("")
    public ResponseEntity<Page<?>> getAll(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size,
                                          @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                                          @RequestParam(name = "order", required = false) String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<PostResponse> postResponses = postService.getAll(pageable);
        return ResponseEntity.status(200).body(postResponses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<?>> search (@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size,
                                           @RequestParam(name = "sort", defaultValue = "id") String sort,
                                           @RequestParam(name = "order", defaultValue = "desc") String order,
                                           @RequestParam(name = "search") String search) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<PostResponse> postResponses = postService.searchPostByName(pageable, search);
        return ResponseEntity.status(200).body(postResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPost(@PathVariable Long id) {
        return ResponseEntity.status(200).body(postService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @ModelAttribute("post") PostRequest postRequest) throws ScheduleException {
        postService.saveOrUpdate(postRequest);
        return ResponseEntity.status(201).body(new ResponseAPI(true,"Thêm mới thành công !"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@Valid @ModelAttribute("post") PostRequest postRequest, @PathVariable Long id) {
        Post post = postService.findById(id);
        postRequest.setId(post.getId());
        postService.saveOrUpdate(postRequest);

        return ResponseEntity.status(201).body(new ResponseAPI(true, "Chỉnh sửa bài viết thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.status(204).body(null);
    }

}
