package com.groupthree.myhomesbe.inquiry.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.groupthree.myhomesbe.auth.model.UserModel;

import javax.annotation.Generated;

@Document(collection = "inquiries")
public class inquiryModel {
    @Id
    private String id;
    private String message;
   @DBRef
    private  user;
}
