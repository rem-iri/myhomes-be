package com.groupthree.myhomesbe.property.loader;

import com.groupthree.myhomesbe.property.model.PropertyModel;
import com.groupthree.myhomesbe.property.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PropertyLoader implements ApplicationRunner {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyLoader(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}