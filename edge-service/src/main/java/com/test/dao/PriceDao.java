package com.test.dao;

import com.test.domain.PriceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDao extends MongoRepository<PriceEntity,String> {

}
