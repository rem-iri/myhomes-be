package com.groupthree.myhomesbe.auth.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Setter
@Getter
public class SignupRequest {
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;


    @NotBlank
    private String accountType;

    @Size(max = 50)
    private String company;

    @Size(max = 20)
    private String plan;

}
