package com.hit.securityjwt.services;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<User> getAllUser();
    User createUser(UserDTO userDTO);
    User deleteUser(Integer id);
}
