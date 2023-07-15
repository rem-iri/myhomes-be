package com.groupthree.myhomesbe.inquiry.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.groupthree.myhomesbe.auth.model.UserModel;

@Getter
@Setter
@Document(collection = "inquiries")
public class InquiryModel {
    @Id
    private String id;
    private String message;
   private String senderId;
   private String receiverId;

    public InquiryModel() {
    }

    public InquiryModel(String id, String message, String sender, String receiver) {
        this.id = id;
        this.message = message;
        this.senderId =senderId;
        this.receiverId =receiverId;
    }
}
