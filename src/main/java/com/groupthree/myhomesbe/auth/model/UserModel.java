package com.groupthree.myhomesbe.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Document(collection = "users")
public class  UserModel {
        @Id
        private String id;

//        @NotBlank
//        @Size(max = 20)
//        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Size(max = 120)
        private String password;

        @NotBlank
        @Size(max = 50)
        private String firstName;

        @NotBlank
        @Size(max = 50)
        private String lastName;

        @NotBlank
        @Size(max = 20)
        private String accountType;

        @Size(max = 50)
        private String company;

        @Size(max = 50)
        private String plan;

        @Size(max = 300)
        private String about;

        private String profilePicture;

//        @DBRef
//        private Set<GrantedAuthority> roles = new HashSet<>();

        public UserModel() {
        }

        public UserModel(
//                String username,
                String email,
                String password,
                String firstName,
                String lastName,
                String accountType,
                String company,
                String plan,
                String about,
                String profilePicture
                )
        {
//                this.username = username;


                this.email = email;
                this.password = password;
                this.firstName = firstName;
                this.lastName = lastName;
                this.accountType = accountType;
                this.company = company;
                this.plan = plan;
                this.about = about;
                this.profilePicture = profilePicture;
        }
        public String getUsername() {
                return email;
        }

        public Set<GrantedAuthority> getRoles() {
                Set<GrantedAuthority> roles = new HashSet<>();
                roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                return roles;
        }

//        public String getId() {
//                return id;
//        }
//
//        public void setId(String id) {
//                this.id = id;
//        }
//
//
//        public void setUsername(String username) {
//                this.username = username;
//        }
//
//        public String getEmail() {
//                return email;
//        }
//
//        public void setEmail(String email) {
//                this.email = email;
//        }
//
//        public String getPassword() {
//                return password;
//        }
//
//        public void setPassword(String password) {
//                this.password = password;
//        }
//        public void setRoles(Set<Role> roles) {
//                this.roles = roles;
//        }
}
