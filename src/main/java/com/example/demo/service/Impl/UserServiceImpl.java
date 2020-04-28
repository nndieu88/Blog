package com.example.demo.service.Impl;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.CommentDto;
import com.example.demo.model.dto.Paging;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.CommentMapper;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.UserCreateRequest;
import com.example.demo.model.request.UserUpdateRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Paging findAllUser(int page) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, 10, Sort.by("dateCreated").descending()));
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users.getContent()) {
            userDtos.add(UserMapper.toUserDto(user));
        }

        Paging paging = new Paging();
        paging.setContent(userDtos);
        paging.setHasNext(users.hasNext());
        paging.setHasPrev(users.hasPrevious());
        paging.setCurrentPage(page + 1);
        paging.setTotalPage(users.getTotalPages() == 0 ? 1 : users.getTotalPages());
        return paging;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("No user found");
        }

        return UserMapper.toUserDto(user.get());
    }

    @Override
    public Paging searchFTS(int page, String searchKey) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(User.class).get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query =
                queryBuilder
                        .keyword()
                        .wildcard()
                        .onFields("userName", "address", "email")
                        .matching("*" + searchKey + "*")
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(query, User.class);

        Paging paging = new Paging();
        page = (page < 0 ? 0 : page);
        page++;
        int limit = 10;
        int totalElement = jpaQuery.getResultSize();

        int totalPage = (totalElement % limit == 0 ? (totalElement / limit) : (totalElement / limit + 1));
        boolean hasNext = (page == totalPage || totalPage == 0) ? false : true;
        boolean hasPrev = (totalPage == 0 || page == 1) ? false : true;

        jpaQuery.setFirstResult((page - 1) * limit)
                .setMaxResults(limit);

        List<UserDto> userDtos = new ArrayList<>();
        for (Object user : jpaQuery.getResultList()) {
            userDtos.add(UserMapper.toUserDto((User) user));
        }

        paging.setContent(userDtos);
        paging.setHasNext(hasNext);
        paging.setHasPrev(hasPrev);
        paging.setCurrentPage(page);
        totalPage = (totalPage == 0 ? 1 : totalPage);
        paging.setTotalPage(totalPage);
        paging.setElement(totalElement);
        return paging;
    }

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        User user = userRepository.findUserByEmail(userCreateRequest.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email already exists in the system");
        }
        User user1 = UserMapper.toUser(userCreateRequest);
        user1.setRole(roleRepository.findById(2L).get());
        try {

            userRepository.save(user1);
        } catch (Exception ex) {
            throw new InternalServerException("Can't create user");
        }
    }

    @Override
    public void updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("Not found user");
        }
        User user1 = UserMapper.toUser(userUpdateRequest, id, user.get().getDateCreated(), user.get().getRole(), user.get().getPassword());
        try {
            userRepository.save(user1);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update user");
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("Not found user");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete user");
        }
    }
}
