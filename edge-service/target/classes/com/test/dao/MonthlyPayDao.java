package com.test.dao;

import com.test.domain.MonthlyPayEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPayDao extends MongoRepository<MonthlyPayEntity,String> {
    MonthlyPayEntity findByYear(String year);

}
