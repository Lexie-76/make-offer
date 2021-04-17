package com.test.service;

import com.test.domain.MonthlyPayEntity;

import java.util.List;

public interface MonthlyPayService {
    List<MonthlyPayEntity> findAll();
    MonthlyPayEntity findByYear(String year);
}
