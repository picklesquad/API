package com.pickle.Service;

import com.pickle.Domain.UserEntity;
import com.pickle.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
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

    public int getIsComplete(String email) {
        return userRepository.getIsComplete(email);
    }
}
