package com.test.service.impl;

import com.test.dao.PriceDao;
import com.test.domain.PriceEntity;
import com.test.service.PriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    @Resource
    private PriceDao priceDao;

    @Override
    public List<PriceEntity> findAll() {
        List<PriceEntity> result = priceDao.findAll();
        return result;
    }
}
