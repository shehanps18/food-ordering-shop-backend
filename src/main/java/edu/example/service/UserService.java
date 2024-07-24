package edu.example.service;

import edu.example.model.UserEntity;
import org.springframework.security.core.userdetails.User;

public interface UserService {
    public UserEntity findUserByJwtToken(String jwt) throws Exception;

    public UserEntity findUserByEmail(String email) throws Exception;

}
