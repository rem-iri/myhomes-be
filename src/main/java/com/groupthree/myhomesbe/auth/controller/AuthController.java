package com.groupthree.myhomesbe.auth.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
public class AuthController {
    @PostMapping("/access-token")
    public Map accessToken(Model model){

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJDbGllbnRJRCI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjE2OTA2MTAwNTB9.zBusF9_YLAFxvt2VAxDgORs5rnY3anHDYrI5hIN_pFA");

        return map;
    }
}
