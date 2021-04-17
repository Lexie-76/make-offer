package com.test.service.impl;

import com.test.dao.HousePriceDao;
import com.test.domain.HousePriceEntity;
import com.test.service.HousePriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HousePriceServiceImpl implements HousePriceService {
    @Resource
    private HousePriceDao housePriceDao;

    @Override
    public List<HousePriceEntity> findAll() {
        List<HousePriceEntity> result = housePriceDao.findAll();
        return result;
    }

    @Override
    public HousePriceEntity findByYear(String year) {
        HousePriceEntity result = housePriceDao.findByYear(year);
        return result;
    }
}
