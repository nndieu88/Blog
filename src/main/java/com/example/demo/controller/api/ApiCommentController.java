package com.example.demo.controller.api;

import com.example.demo.model.api.BasicApiResult;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.request.CommentCreateRequest;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admins/comments")
@RestController
public class ApiCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer page) {
        BasicApiResult result = new BasicApiResult();
        try {
            page = page == null ? 0 : page;
            Paging paging = commentService.getAll(page);

            result.setSuccess(true);
            result.setMessage("Successfull");
            result.setData(paging);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentCreateRequest comment) {
        BasicApiResult result = new BasicApiResult();
        try {
            commentService.createComment(comment);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        BasicApiResult result = new BasicApiResult();
        try {
            commentService.deleteComment(id);

            result.setSuccess(true);
            result.setMessage("Successfull");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
