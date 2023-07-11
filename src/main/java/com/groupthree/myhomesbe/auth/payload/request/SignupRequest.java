package com.groupthree.myhomesbe.auth.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Setter
@Getter
public class SignupRequest {
//    @NotBlank
//    @Size(min = 3, max = 20)
//    private String username;
 
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
    
//    private Set<String> roles;

    @NotBlank
    private String accountType;

    @Size(max = 50)
    private String company;

    @Size(max = 20)
    private String plan;
  
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Set<String> getRoles() {
//      return this.roles;
//    }
//
//    public void setRole(Set<String> roles) {
//      this.roles = roles;
//    }
//
//    public String getAccountType() {
//        return accountType;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }
}
