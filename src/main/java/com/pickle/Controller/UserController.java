package com.pickle.Controller;

import com.pickle.Domain.UserEntity;
import com.pickle.Domain.Wrapper;
import com.pickle.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andrikurniawan.id@gmail.com on 3/17/2016.
 */
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Wrapper login(@RequestParam("email")String email){
        UserEntity user = userService.validation(email);
        if(user == null){
            return new Wrapper(200, "Gagal", null);
        }else{
            return new Wrapper(200, "Sukses", null);
        }
    }
}
