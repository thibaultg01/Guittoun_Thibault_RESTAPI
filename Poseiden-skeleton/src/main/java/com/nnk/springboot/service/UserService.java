package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.model.User;

public interface UserService {
    List<User> findAll();
    User getByIdOrThrow(Integer id);
    User save(User user);
    User update(Integer id, User user);
    void delete(Integer id);
}