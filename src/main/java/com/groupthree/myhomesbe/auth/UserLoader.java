package com.groupthree.myhomesbe.auth;

import com.groupthree.myhomesbe.auth.model.UserModel;
import com.groupthree.myhomesbe.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    public UserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int size = userRepository.findAll().size();

        if(size == 0) {
            userRepository.save(new UserModel(

            ));
        }
    }
}