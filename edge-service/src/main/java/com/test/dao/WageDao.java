package com.test.dao;

import com.test.domain.WageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageDao extends MongoRepository<WageEntity,String> {

}
