package com.example.demo.controller;

import com.example.demo.model.dto.*;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("admin")
@Controller
public class AdminController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    //admin-page
    @GetMapping("")
    public String index(Model model) {
        try {
            isUser(model);
            return "admin/home";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    // admin-catelories-page
    @GetMapping("/categories")
    public String category(Model model, Integer page) {
        try {
            model.addAttribute("isCategory", true);

            int currentPage = (page == null ? 0 : page - 1);
            Paging categories = categoryService.getAll(currentPage);
            model.addAttribute("categories", categories);
            return "admin/category";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/categories/search")
    public String searchCategory(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String key) {
        try {
            model.addAttribute("isSearch", true);

            page = page == null ? 0 : page - 1;
            Paging paging = categoryService.getCategoryFTS(page, key);
            model.addAttribute("categories", paging);
            model.addAttribute("key", key);
            return "admin/category";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    // admin-comment-page
    @GetMapping("/comments")
    public String comments(Model model, Integer page) {
        try {
            model.addAttribute("isComment", true);

            int currentPage = (page == null ? 0 : page - 1);
            Paging paging = commentService.getAll(currentPage);
            model.addAttribute("comments", paging);
            return "admin/comment";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/comments/search")
    public String searchComment(Model model, @PageableDefault(size = 10) Integer page, @RequestParam(required = false) String key) {
        try {
            model.addAttribute("isSearch", true);

            int currentPage = (page == null ? 0 : page - 1);
            Paging paging = commentService.searchFTS(currentPage, key);
            model.addAttribute("comments", page);
            model.addAttribute("key", key);
            return "admin/category";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    //admin-users-page
    @GetMapping("/users")
    public String user(Model model, Integer page) {
        try {
            model.addAttribute("isUser", true);

            int currentPage = (page == null ? 0 : page - 1);
            Paging paging = userService.findAllUser(currentPage);
            model.addAttribute("users", paging);
            return "admin/user";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/users/search")
    public String searchUser(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String key) {
        try {
            model.addAttribute("isSearch", true);

            page = page == null ? 0 : page - 1;
            Paging paging = userService.searchFTS(page, key);
            model.addAttribute("users", paging);
            model.addAttribute("key", key);
            return "admin/user";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/posts")
    public String post(Model model, Integer page) {
        try {
            model.addAttribute("isPost", true);

            int currentPage = (page == null ? 0 : page - 1);
            Paging paging = postService.getAll(currentPage);
            model.addAttribute("posts", paging);

            addCategory(model);
            return "admin/post";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("posts/search")
    public String searchPost(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String key) {
        try {
            model.addAttribute("isSearch", true);
            model.addAttribute("key", key);

            page = page == null ? 0 : page - 1;
            Paging paging = postService.searchFTS(page, key);
            model.addAttribute("posts", paging);

            return "admin/post";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    public void isUser(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            model.addAttribute("isUser", true);
            addUser(model);
        }
    }

    public void addUser(Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("currentUser", UserMapper.toUserDto(userDetails.getUser()));
    }

    public void addCategory(Model model) {
        List<CategoryDto> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);
    }
}
