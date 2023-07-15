package com.groupthree.myhomesbe.inquiry.controller;

import com.groupthree.myhomesbe.inquiry.model.InquiryModel;
import com.groupthree.myhomesbe.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping ("/api/buyer")
public class inquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping(value = "/save")
    private String saveInquiry(@RequestBody InquiryModel inquiry){
        inquiryService.savemessage(inquiry);
        return inquiry.getId();
    }
    @GetMapping(value= "/getAll")
    public Iterable<InquiryModel>getInquiries(){
    return inquiryService.listAll();
    }
}
