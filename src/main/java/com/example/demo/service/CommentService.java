package com.example.demo.service;

import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.request.CommentCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public Paging getAll(int page);

    public List<CommentDto> getListComment(Long id);

    public Paging searchFTS(int page,String searchKey);

    public CommentDto createComment(CommentCreateRequest commentCreateRequest);

    public void deleteComment(Long id);
}
