package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/makeoffer")
public class ChartController{

    @RequestMapping("/pricedetail")
    public String priceDetail(){
        return "pricedetail";
    }

    @RequestMapping(value = "/wagedetail")
    public String wageDetail(){
        return "wagedetail";
    }

    @RequestMapping(value = "/housepricedetail")
    public String housePriceDetail(){
        return "housepricedetail";
    }

    @RequestMapping(value = "/monthlypaydetail")
    public String monthlyPayDetail(){
        return "monthlypaydetail";
    }

    @RequestMapping(value = "/commercialloan")
    public String commercialLoan(){
        return "commercialloan";
    }

    @RequestMapping(value = "/commercialloan2")
    public String commercialLoan2(){
        return "commercialloan2";
    }

    @RequestMapping(value = "/providentfund")
    public String providentFund(){
        return "providentfund";
    }

    @RequestMapping(value = "/providentfund2")
    public String providentFund2(){
        return "providentfund2";
    }

    @RequestMapping(value = "/team")
    public String teamIntroduction(){
        return "team";
    }

}


