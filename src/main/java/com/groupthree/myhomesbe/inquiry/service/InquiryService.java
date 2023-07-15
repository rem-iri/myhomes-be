package com.groupthree.myhomesbe.inquiry.service;

import com.groupthree.myhomesbe.inquiry.model.InquiryModel;
import com.groupthree.myhomesbe.inquiry.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class InquiryService {
    @Autowired
    private InquiryRepository repo;

    public void savemessage(InquiryModel inquiry) {
        repo.save(inquiry);
    }

    public Iterable<InquiryModel>listAll(){
        return this.repo.findAll();
    }
}
