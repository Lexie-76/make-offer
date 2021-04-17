package com.test.service;

import com.test.domain.HousePriceEntity;

import java.util.List;

public interface HousePriceService {
    List<HousePriceEntity> findAll();
    HousePriceEntity findByYear(String year);
}
