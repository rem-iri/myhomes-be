package com.groupthree.myhomesbe.property.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

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

}
