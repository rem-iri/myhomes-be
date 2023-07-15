package com.groupthree.myhomesbe.property.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;



@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryModel {
    @Id
    private String id;
    private String buyer_id;
    private String message;
    private String phoneNumber;
    private String email;
    private String date;
}