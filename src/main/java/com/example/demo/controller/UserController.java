package com.example.demo.controller;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.PostDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommentService;
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

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            List<PostDto> post1 = postService.getThreeListPost(1);
            model.addAttribute("post1", post1);
            List<PostDto> post2 = postService.getThreeListPost(2);
            model.addAttribute("post2", post2);
            List<PostDto> post3 = postService.getThreeListPost(3);
            model.addAttribute("post3", post3);
            List<PostDto> post4 = postService.getFiveListPost(4);
            model.addAttribute("post4", post4);
            List<PostDto> post5 = postService.getThreeListPost(5);
            model.addAttribute("post5", post5);
            List<PostDto> post6 = postService.getThreeListPost(6);
            model.addAttribute("post6", post6);

            PostDto postDto = postService.getOneImportant(post1.get(0).getMetaCategory());
            model.addAttribute("postImportant", postDto);

            addCategory(model);
            isUser(model);
            return "user/index";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/list/{name}")
    public String listPost(Model model, @PathVariable String name) {
        try {
            List<PostDto> postDtos = postService.getPostbyMetaCate(name);
            model.addAttribute("posts", postDtos);

            PostDto postDto = postService.getOneImportant(postDtos.get(0).getMetaCategory());
            model.addAttribute("postImportant", postDto);

            return "user/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/list-all/{name}")
    public String ListAll(Model model, @PathVariable String name, @RequestParam(required = false) Integer page) {
        try {
            int currentPage = (page == null ? 0 : page - 1);
            Paging posts = postService.getAllPostByCategory(name, currentPage);
            model.addAttribute("posts", posts);

            List<PostDto> postDtos = postService.getPostbyMetaCate(name);
            PostDto postDto = postService.getOneImportant(postDtos.get(0).getMetaCategory());
            model.addAttribute("postImportant", postDto);

            return "user/all-list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/search")
    public String searchPost(Model model,
                             @RequestParam(required = false) String key,
                             @RequestParam(required = false) Integer page) {

        try {
            model.addAttribute("isSearch", true);
            model.addAttribute("key", key);

            if (key != null) {
                model.addAttribute("isKey", true);
            }

            page = page == null ? 0 : page - 1;
            Paging paging = postService.searchFTS(page, key);
            model.addAttribute("posts", paging);
            return "user/search";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        try {
            isUser(model);
            return "user/login";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        try {
            return "user/register";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
    }


    @GetMapping("/info/{name}")
    public String info(@PathVariable String name, Model model) {
        try {
            PostDto post = postService.getPostbyMetaTitle(name);
            model.addAttribute("post", post);

            List<CommentDto> comments = commentService.getListComment(post.getId());
            model.addAttribute("comments", comments);

            addCategory(model);
            isUser(model);
            return "user/info";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "user/404";
        }
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
