package com.pickle.Service;

import com.pickle.Domain.UserEntity;
import com.pickle.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services provided by UserRepository
 * @author Andri Kurniawan
 * @author Syukri Mullia Adil P.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity validation(String email){
        UserEntity user = userRepository.findByEmail(email);
        return user;
    }

    public UserEntity getUserById(int id){
        UserEntity user = userRepository.findById(id);
        return user;
    }

    public UserEntity getUserByApiToken(String token) {
        return userRepository.findByApiToken(token);
    }

    public UserEntity getUserByFbToken(String token) {
        return userRepository.findByFbToken(token);
    }

    public UserEntity getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public int getIsComplete(String email) {
        return userRepository.getIsComplete(email);
    }

    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
