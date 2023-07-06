package com.groupthree.myhomesbe.property.repository;

import com.groupthree.myhomesbe.property.model.PropertyModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<PropertyModel, String> {

}
