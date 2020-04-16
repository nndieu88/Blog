package com.example.demo.controller;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //admin-page
    @GetMapping("")
    public String index(Model model) {
        isUser(model);
        return "/admin/home";
    }

    //admin-catelories-page
    @GetMapping("/categories")
    public String category(Model model, @RequestParam(required = false) Integer page) {
        int currentPage = (page == null ? 0 : page - 1);
        Paging categories = categoryService.getAll(currentPage);
        model.addAttribute("category", categories);
        return "/admin/category";
    }

    //admin-users-page
    @GetMapping("/users")
    public String user(Model model) {
        List<UserDto> users = userService.findAllUser();
        model.addAttribute("users", users);
        return "/admin/user";
    }

    //admin-products-page
    @GetMapping("/post")
    public String post(Model model, @RequestParam(required = false) Integer page) {
        model.addAttribute("isPost", true);

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAll(currentPage);
        model.addAttribute("posts", posts);

        addCategory(model);
        return "/admin/post";
    }

    @GetMapping("post/search")
    public String postByTitle(Model model,
                              @RequestParam(required = false) String key,
                              Integer page) {
        model.addAttribute("isSearch", true);

        addCategory(model);

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAllPostByTitle(key, currentPage);
        if (posts.getContent().size() == 0) {
            model.addAttribute("isPost", false);
        }
        model.addAttribute("products", posts);

        model.addAttribute("key", key);

        isUser(model);
        return "/admin/products";
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
