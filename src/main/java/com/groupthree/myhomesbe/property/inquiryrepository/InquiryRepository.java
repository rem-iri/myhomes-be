package com.groupthree.myhomesbe.property.inquiryrepository;

import com.groupthree.myhomesbe.property.model.PropertyModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.groupthree.myhomesbe.property.model.InquiryModel;
public interface InquiryRepository extends MongoRepository <InquiryModel, String>  {
}
