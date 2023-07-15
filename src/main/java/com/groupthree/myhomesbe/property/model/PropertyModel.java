package com.groupthree.myhomesbe.property.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "properties")
public class  PropertyModel {
    @Id
    private String id;
    private String user_id;
    private String listingTitle;
    private String propertyType;
    private String description;
    private String furnishing;
    private String saleType;
    private int bath;
    private int bedroom;
    private double price;
    private double area;
    private String houseNumber;
    private String street;
    private String village;
    private String city;
    private String province;
    private String region;
    private String dateCreated;
    private boolean isSold;
    private List<ImageModel> images;
    private List<InquiryModel> inquiries;
    private String dateSold;


//    public List<ImageModel> getImages() {
//        List<ImageModel> transformedImages = this.images.stream().map(e -> {
//            e.setImageUrl(e.getImageUrl().contains("://") ?
//                    e.getImageUrl() :
//                    "http://localhost:5556/api/upload/get/" + e.getImageUrl());
//
//            return e;
//        }).collect(Collectors.toList());
//        return this.images;
//    }
}
