package com.example.demo.controller.api;

import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admins/products")
public class ApiPostController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getAllPost(@RequestParam Integer page) {
        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAll(currentPage);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        PostDto post = postService.getOne(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostCreateRequest post) {
        postService.createPost(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest post) {
        postService.updatePost(post,id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
