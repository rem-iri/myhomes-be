package com.groupthree.myhomesbe.property.controller;

import com.groupthree.myhomesbe.auth.repository.UserRepository;
import com.groupthree.myhomesbe.property.model.PropertyModel;
import com.groupthree.myhomesbe.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyRepository propertyRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public List<PropertyModel> all() {
        return propertyRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropertyModel> getById(@PathVariable("id") String id) {
        Optional<PropertyModel> property = propertyRepository.findById(id);
        return new ResponseEntity<>(property.get(), HttpStatus.OK);
    }
}
