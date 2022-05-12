package com.hit.securityjwt.services.imp;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.dto.UserDTO;
import com.hit.securityjwt.exceptions.BadRequestException;
import com.hit.securityjwt.exceptions.NotFoundException;
import com.hit.securityjwt.repository.UserRepository;
import com.hit.securityjwt.services.IUserService;
import com.hit.securityjwt.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new BadRequestException("Duplicate user");
        }
        User user1 = new User();
        Convert.fromUserDTOToUser(userDTO, user1);
        return userRepository.save(user1);
    }

    @Override
    public User deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No user");
        }
        userRepository.delete(user.get());
        return user.get();
    }
}
