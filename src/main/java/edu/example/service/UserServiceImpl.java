package edu.example.service;

import edu.example.config.JwtProvider;
import edu.example.model.UserEntity;
import edu.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public JwtProvider jwtProvider;

    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        UserEntity user= findUserByEmail(email);
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws Exception {
        UserEntity user = userRepository.findByEmail(email);

        if (user==null){
            throw new Exception("user not found");
        }

        return null;
    }
}
