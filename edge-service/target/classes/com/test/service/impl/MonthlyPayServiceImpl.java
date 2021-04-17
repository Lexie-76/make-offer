package com.test.service.impl;

import com.test.dao.MonthlyPayDao;
import com.test.domain.MonthlyPayEntity;
import com.test.service.MonthlyPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MonthlyPayServiceImpl implements MonthlyPayService {
    @Resource
    private MonthlyPayDao monthlyPayDao;

    @Override
    public List<MonthlyPayEntity> findAll() {
        List<MonthlyPayEntity> result = monthlyPayDao.findAll();
        return result;
    }

    @Override
    public MonthlyPayEntity findByYear(String year) {
        MonthlyPayEntity result = monthlyPayDao.findByYear(year);
        return result;
    }
}
