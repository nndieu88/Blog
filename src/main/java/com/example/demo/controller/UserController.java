package com.example.demo.controller;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("")
@Controller
public class UserController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) Integer page) {

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAll(currentPage);
        model.addAttribute("posts", posts);

        addCategory(model);

        isUser(model);
        return "/user/index";
    }

    @GetMapping("/category")
    public String indexByCategory(Model model, @RequestParam(required = false) Integer cateid,
                                  @RequestParam(required = false) Integer page) {
        model.addAttribute("isCate", true);

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAllPostByCategory(cateid, currentPage);
        if (posts.getContent().size() == 0) {
            model.addAttribute("isPost", false);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("cateid", cateid);

        addCategory(model);

        isUser(model);
        return "/user/index";
    }

    @GetMapping("/all")
    public String allBlog(Model model, @RequestParam(required = false) Integer page) {
        model.addAttribute("isBlog", true);

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAll(currentPage);
        model.addAttribute("posts", posts);
        return "/user/list";
    }

    @GetMapping("/search")
    public String indexByTitle(Model model,
                               @RequestParam(required = false) String key,
                               Integer page) {
        model.addAttribute("isSearch", true);

        int currentPage = (page == null ? 0 : page - 1);
        Paging posts = postService.getAllPostByTitle(key, currentPage);
        if (posts.getContent().size() == 0) {
            model.addAttribute("isPost", false);
        }
        model.addAttribute("posts", posts);

        model.addAttribute("key", key);

//        addCategory(model);
//
//        isUser(model);
        return "/user/list";
    }

    @GetMapping("/login")
    public String login(Model model) {
        isUser(model);
        return "/user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/user/register";
    }


    @GetMapping("/info/{id}")
    public String info(@PathVariable Long id, Model model) {
        PostDto post = postService.getOne(id);
        model.addAttribute("post", post);

        addCategory(model);

        isUser(model);
        return "/user/info";
    }

    @GetMapping("/404")
    public String err404() {
        return "user/404";
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
