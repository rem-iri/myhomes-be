package com.groupthree.myhomesbe.auth.controller;

import com.groupthree.myhomesbe.auth.model.UserModel;
import com.groupthree.myhomesbe.auth.payload.request.LoginRequest;
import com.groupthree.myhomesbe.auth.payload.request.SignupRequest;
import com.groupthree.myhomesbe.auth.payload.response.ErrorResponse;
import com.groupthree.myhomesbe.auth.payload.response.JwtResponse;
import com.groupthree.myhomesbe.auth.payload.response.MessageResponse;
import com.groupthree.myhomesbe.auth.repository.UserRepository;
import com.groupthree.myhomesbe.security.jwt.AuthEntryPointJwt;
import com.groupthree.myhomesbe.security.jwt.JwtUtils;
import com.groupthree.myhomesbe.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)

public class  AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        System.out.println("ROLE " + roles.size());

        UserModel user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getEmail()));

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAccountType(),
                user.getCompany(),
                user.getPlan()
        ));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest().body(new ErrorResponse("Email already exists"));
        }

        // Create new user's account

        UserModel user;

        if(signUpRequest.getAccountType().equals("seller")) {
            user = new UserModel(
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getFirstName(),
                    signUpRequest.getLastName(),
                    signUpRequest.getAccountType(),
                    signUpRequest.getCompany(),
                    signUpRequest.getPlan(),
                    null,
                    null
            );
        } else {
            user = new UserModel(
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getFirstName(),
                    signUpRequest.getLastName(),
                    signUpRequest.getAccountType(),
                    null,
                    null,
                    null,
                    null
            );
        }

//        Set<String> strRoles = signUpRequest.getRoles();
//        Set roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }

//        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/seller-profile/{id}")
    public ResponseEntity<?> getSellerProfile(@PathVariable("id") String id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!user.getAccountType().equals("seller")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a seller."));
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/seller-profile/{id}")
    public ResponseEntity<?> updateSellerProfile(@PathVariable("id") String id, @RequestBody UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("seller")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a seller."));
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setCompany(updatedUser.getCompany());
        existingUser.setAbout(updatedUser.getAbout());

        userRepository.save(existingUser);

        return ResponseEntity.ok(new MessageResponse("Seller profile updated successfully!"));
    }

    @GetMapping("/seller-profile/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable("id") String id) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("seller")) {
            return ResponseEntity.badRequest().body(null);
        }

        String profilePicturePath = existingUser.getProfilePicture();

        try {
            Path profilePicture = Paths.get(profilePicturePath);
            Resource resource = new UrlResource(profilePicture.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/seller-profile/{id}/profile-picture")
    public ResponseEntity<?> updateSellerProfilePicture(
            @PathVariable("id") String id,
            @RequestParam("file") MultipartFile file
    ) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("seller")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a seller."));
        }

        try {

            String profilePicture = saveProfilePicture(file);


            existingUser.setProfilePicture(profilePicture);


            userRepository.save(existingUser);

            return ResponseEntity.ok(new MessageResponse("Profile picture updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Failed to update profile picture."));
        }
    }
    private String saveProfilePicture(MultipartFile file) throws IOException {
        String profilePictureDirectory = "src/main/resources/uploads/profile";
        String profilePictureFileName = file.getOriginalFilename();

        Path directory = Paths.get(profilePictureDirectory);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path destinationFile = directory.resolve(profilePictureFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return profilePictureDirectory + "/" + profilePictureFileName;
    }
    @GetMapping("/buyer-profile/{id}")
    public ResponseEntity<?> getBuyerProfile(@PathVariable("id") String id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!user.getAccountType().equals("buyer")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a buyer."));
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/buyer-profile/{id}")
    public ResponseEntity<?> updateBuyerProfile(@PathVariable("id") String id, @RequestBody UserModel updatedUser) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("buyer")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a buyer."));
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        userRepository.save(existingUser);

        return ResponseEntity.ok(new MessageResponse("Buyer profile updated successfully!"));
    }

    @GetMapping("/buyer-profile/{id}/profile-picture")
    public ResponseEntity<Resource> getBuyerProfilePicture(@PathVariable("id") String id) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("buyer")) {
            return ResponseEntity.badRequest().body(null);
        }

        String profilePicturePath = existingUser.getProfilePicture();

        try {
            Path profilePicture = Paths.get(profilePicturePath);
            Resource resource = new UrlResource(profilePicture.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/buyer-profile/{id}/profile-picture")
    public ResponseEntity<?> updateBuyerProfilePicture(
            @PathVariable("id") String id,
            @RequestParam("file") MultipartFile file
    ) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

        if (!existingUser.getAccountType().equals("buyer")) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid request. User is not a buyer."));
        }

        try {
            String profilePicture = saveProfilePicture(file);
            existingUser.setProfilePicture(profilePicture);
            userRepository.save(existingUser);

            return ResponseEntity.ok(new MessageResponse("Profile picture updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Failed to update profile picture."));
        }
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String userId) {
        Optional<UserModel> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/sellers")
    public ResponseEntity<List<UserModel>> getAllSellers() {
        List<UserModel> sellers = userRepository.findByAccountType("seller");
        return ResponseEntity.ok(sellers);
    }

}