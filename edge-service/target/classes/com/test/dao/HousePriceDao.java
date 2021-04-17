package com.test.dao;

import com.test.domain.HousePriceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousePriceDao extends MongoRepository<HousePriceEntity,String> {
    HousePriceEntity findByYear(String year);

}
