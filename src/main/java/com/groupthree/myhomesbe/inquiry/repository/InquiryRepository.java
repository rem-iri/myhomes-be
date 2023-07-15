package com.groupthree.myhomesbe.inquiry.repository;

import com.groupthree.myhomesbe.inquiry.model.InquiryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface InquiryRepository extends MongoRepository<InquiryModel,String> {

}
