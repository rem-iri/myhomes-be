package com.groupthree.myhomesbe.inquiry.controller;

import com.groupthree.myhomesbe.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping (value = "/api/buyer", produces = MediaType.APPLICATION_JSON_VALUE)
public class inquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping(value = "/save")
    private String saveInquiry(@RequestBody )
}
