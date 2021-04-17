package com.test.controller;

import com.test.thrift.ServiceProvider;
import com.test.thrift.forecast.ForecastService;
import com.test.thrift.spider.SpiderService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping("/makeoffer")
public class DataController {
    @Resource
    private ServiceProvider serviceProvider;

    @GetMapping(value = "/index")
    public String index() {
        SpiderService.Iface spiderService = serviceProvider.getSpiderService();
        try {
            spiderService.SpiderAllUrls();
        } catch (TException e) {
            e.printStackTrace();
            return null;
        }
        ForecastService.Iface forecastService = serviceProvider.getForecastService();
        try {
            forecastService.house_price();
            forecastService.monthly_pay();
        } catch (TException e) {
            e.printStackTrace();
            return null;
        }
        return "index";
    }

    @RequestMapping(value = "/index2")
    public String index2() {
        return "index2";
    }

}
