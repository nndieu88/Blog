package com.example.demo.controller.api;

import com.example.demo.model.api.BasicApiResult;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.request.PostCreateRequest;
import com.example.demo.model.request.PostUpdateRequest;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admins/posts")
public class ApiPostController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getAllPost(@RequestParam Integer page) {
        BasicApiResult result = new BasicApiResult();
        try {
            int currentPage = (page == null ? 0 : page - 1);
            Paging posts = postService.getAll(currentPage);

            result.setSuccess(true);
            result.setMessage("Successfull");
            result.setData(posts);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            PostDto post = postService.getOne(id);

            result.setSuccess(true);
            result.setMessage("Successfull");
            result.setData(post);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostCreateRequest post) {
        BasicApiResult result = new BasicApiResult();
        try {
            postService.createPost(post);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@Valid @PathVariable Long id, @RequestBody PostUpdateRequest post) {
        BasicApiResult result = new BasicApiResult();
        try {
            postService.updatePost(post, id);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            postService.deletePost(id);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
