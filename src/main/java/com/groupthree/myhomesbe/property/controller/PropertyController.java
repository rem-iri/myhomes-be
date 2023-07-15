package com.groupthree.myhomesbe.property.controller;

import com.groupthree.myhomesbe.auth.repository.UserRepository;
import com.groupthree.myhomesbe.property.model.ImageModel;
import com.groupthree.myhomesbe.property.model.InquiryModel;
import com.groupthree.myhomesbe.property.model.PropertyModel;
import com.groupthree.myhomesbe.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    PropertyRepository propertyRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public List<PropertyModel> all() {
        List<PropertyModel> properties =  propertyRepository.findAll();


        List<PropertyModel> transformedProperties = properties.stream().map(prop -> {
            prop.setImages(prop.getImages().stream().map(image -> {
                image.setImageUrl(image.getImageUrl().contains("://") ?
                        image.getImageUrl() :
                        "http://localhost:5556/api/upload/get/" + image.getImageUrl());

                return image;
            }).collect(Collectors.toList()));

            return prop;
        }).collect(Collectors.toList());

        return transformedProperties;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropertyModel> createTutorial(@RequestBody PropertyModel property) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            List<ImageModel> images = property.getImages().stream().map(e -> {
                return new ImageModel(null, e.getImageUrl());
            }).collect(Collectors.toList());

            List<InquiryModel> inquiries = new ArrayList<>();

            PropertyModel newProperty = propertyRepository.save(new PropertyModel(
                    null,
                    property.getUser_id(),
                    property.getListingTitle(),
                    property.getPropertyType(),
                    property.getDescription(),
                    property.getFurnishing(),
                    property.getSaleType(),
                    property.getBath(),
                    property.getBedroom(),
                    property.getPrice(),
                    property.getArea(),
                    property.getHouseNumber(),
                    property.getStreet(),
                    property.getVillage(),
                    property.getCity(),
                    property.getProvince(),
                    property.getRegion(),
                    String.valueOf(timestamp.getTime()),
                    property.isSold(),
                    images,
                    inquiries,
                    null
                    ));
            return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            propertyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateSold/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropertyModel> updateSold(@PathVariable("id") String id) {
        Optional<PropertyModel> propertyQuery = propertyRepository.findById(id);

        if (propertyQuery.isPresent()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            PropertyModel property = propertyQuery.get();
            property.setSold(true);
            property.setDateSold(String.valueOf(timestamp.getTime()));
            return new ResponseEntity<>(propertyRepository.save(property), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PropertyModel> getById(@PathVariable("id") String id) {
        Optional<PropertyModel> propertyQuery = propertyRepository.findById(id);

        PropertyModel property = propertyQuery.get();

        property.setImages(property.getImages().stream().map(image -> {
            image.setImageUrl(image.getImageUrl().contains("://") ?
                    image.getImageUrl() :
                    "http://localhost:5556/api/upload/get/" + image.getImageUrl());

            return image;
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(property, HttpStatus.OK);
    }
}
