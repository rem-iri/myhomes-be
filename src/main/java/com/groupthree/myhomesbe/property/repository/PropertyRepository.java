package com.groupthree.myhomesbe.property.repository;

import com.groupthree.myhomesbe.property.model.PropertyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PropertyRepository extends MongoRepository<PropertyModel, String> {
    List<PropertyModel> findByUserId(String userId);
}
