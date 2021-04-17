package com.test.service.impl;



import com.test.dao.WageDao;
import com.test.domain.WageEntity;
import com.test.service.WageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WageServiceImpl implements WageService {

    @Resource
    private WageDao wageDao;

    @Override
    public List<WageEntity> findAll() {
        List<WageEntity> result = wageDao.findAll();
        return result;
    }
}
