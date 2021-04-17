package com.test.controller;

import com.test.domain.*;
import com.test.service.HousePriceService;
import com.test.service.MonthlyPayService;
import com.test.service.PriceService;
import com.test.service.WageService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/makeoffer")
public class MongoController {
    @Resource
    private PriceService priceService;
    @Resource
    private WageService wageService;
    @Resource
    private HousePriceService housePriceService;
    @Resource
    private MonthlyPayService monthlyPayService;

    @GetMapping("/priceData1")
    @ResponseBody
    public Data1 getPriceData1(){
        Data1 md = this.getPrice1();
        return md;
    }
    @GetMapping("/priceData2")
    @ResponseBody
    public List<Data2> getPriceData2()
    {
        return this.getPrice2();
    }


    @GetMapping("/wageData1")
    @ResponseBody
    public Data1 getWageData1(){
        Data1 md = this.getWage1();
        return md;
    }
    @GetMapping("/wageData2")
    @ResponseBody
    public List<Data2> getWageData2()
    {
        return this.getWage2();
    }


    @GetMapping("/housePriceData1")
    @ResponseBody
    public Data1 getHousePriceData1(){
        Data1 md = this.getHousePrice1();
        return md;
    }
    @GetMapping("/housePriceData2")
    @ResponseBody
    public List<Data2> getHousePriceData2()
    {
        return this.getHousePrice2();
    }


    @GetMapping("/monthlyPayData1")
    @ResponseBody
    public Data1 getMonthlyPayData1(){
        Data1 md = this.getMonthlyPay1();
        return md;
    }
    @GetMapping("/monthlyPayData2")
    @ResponseBody
    public List<Data2> getMonthlyPayData2()
    {
        return this.getMonthlyPay2();
    }

    @RequestMapping("/commercialFirstData")
    @ResponseBody
    public List<Data4> commercialFirst(){
        return this.getCommercialFirstDataList();
    }

    @RequestMapping("/commercialMonthlyData")
    @ResponseBody
    public List<Data3> commercialMonthly(){
        return this.getCommercialMonthlyContribution();
    }


    @RequestMapping("/providentFirstData")
    @ResponseBody
    public List<Data3> providentFirst(){
        return this.getProvidentFirstPay();

    }

    @RequestMapping("/providentMonthlyData")
    @ResponseBody
    public List<Data3> loanPriceYear(){
        return this.getProvidentMonthlyContribution();

    }

    public Data1 getPrice1(){
        List<PriceEntity> priceList = priceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int year=2009;year<2019;year++){
            names.add(year);
        }
        for(int i=0;i<priceList.size();i++){
            value.add(Float.parseFloat(priceList.get(i).getPrice()));
        }
        Data1 md = new Data1(names,value);
        return md;
    }

    public List<Data2> getPrice2(){
        List<PriceEntity> priceList = priceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int year=2009;year<2019;year++){
            names.add(year);
        }
        for(int i=0;i<priceList.size();i++){
            value.add(Float.parseFloat(priceList.get(i).getPrice()));
        }
        List<Data2> md2s = new ArrayList<>();
        for(int i=0;i<names.size();i++){
            md2s.add(new Data2(names.get(i),value.get(i)));
        }
        return md2s;
    }

    public Data1 getWage1(){
        List<WageEntity> wageList = wageService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int year=2014;year<2021;year++){
            names.add(year);
        }
        for(int i=0;i<wageList.size();i++){
            value.add(Float.parseFloat(wageList.get(i).getWage()));
        }
        Data1 md = new Data1(names,value);
        return md;
    }

    public List<Data2> getWage2(){
        List<WageEntity> wageList = wageService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int year=2014;year<2021;year++){
            names.add(year);
        }
        for(int i=0;i<wageList.size();i++){
            value.add(Float.parseFloat(wageList.get(i).getWage()));
        }
        List<Data2> md2s = new ArrayList<>();
        for(int i=0;i<names.size();i++){
            md2s.add(new Data2(names.get(i),value.get(i)));
        }
        return md2s;
    }

    public Data1 getHousePrice1(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            value.add(Float.parseFloat(housePriceList.get(i).getPrice()));
        }
        Data1 md = new Data1(names,value);
        return md;
    }

    public List<Data2> getHousePrice2(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            value.add(Float.parseFloat(housePriceList.get(i).getPrice()));
        }
        List<Data2> md2s = new ArrayList<>();
        for(int i=0;i<names.size();i++){
            md2s.add(new Data2(names.get(i),value.get(i)));
        }
        return md2s;
    }

    public Data1 getMonthlyPay1(){
        List<MonthlyPayEntity> monthlyPayList = monthlyPayService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<monthlyPayList.size();i++){
            names.add(Integer.parseInt(monthlyPayList.get(i).getYear()));
            value.add(Float.parseFloat(monthlyPayList.get(i).getWage()));
        }
        Data1 md = new Data1(names,value);
        return md;
    }

    public List<Data2> getMonthlyPay2(){
        List<MonthlyPayEntity> monthlyPayList = monthlyPayService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<monthlyPayList.size();i++){
            names.add(Integer.parseInt(monthlyPayList.get(i).getYear()));
            value.add(Float.parseFloat(monthlyPayList.get(i).getWage()));
        }
        List<Data2> md2s = new ArrayList<>();
        for(int i=0;i<names.size();i++){
            md2s.add(new Data2(names.get(i),value.get(i)));
        }
        return md2s;
    }

    public List<Data3> getCommercialFirstPay(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            value.add(Float.parseFloat(housePriceList.get(i).getPrice()));
        }
        List<Data3> md3s = new ArrayList<>();
        int square = 0;
        for(int i=0;i<names.size();i++){
            double firstPay = (value.get(i))* 0.3 * square;
            md3s.add(new Data3(square,names.get(i),firstPay));
        }
        square = 50;
        for(int i=0;i<names.size();i++){
            double firstPay = (value.get(i))* 0.3 * square;
            md3s.add(new Data3(square,names.get(i),firstPay));
        }
        for(int j = 70;j<=170;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double firstPay = (value.get(i))* 0.3 * square;
                md3s.add(new Data3(square,names.get(i),firstPay));
            }
        }
        square = 200;
        for(int i=0;i<names.size();i++){
            double firstPay = (value.get(i))* 0.3 * square;
            md3s.add(new Data3(square,names.get(i),firstPay));
        }
        return md3s;
    }

    public List<Data3> getCommercialMonthlyContribution(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<MonthlyPayEntity> monthlyPayList = monthlyPayService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> valuePrice = new ArrayList<>();
        List<Float> valueWage = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            valuePrice.add(Float.parseFloat(housePriceList.get(i).getPrice()));
            valueWage.add(Float.parseFloat(monthlyPayList.get(i).getWage()));
        }
        List<Data3> md3s = new ArrayList<>();
        int square = 0;
        for(int i=0;i<names.size();i++){
            double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
            md3s.add(new Data3(square,names.get(i),monthlyContribution));
        }
        square = 50;
        for(int i=0;i<names.size();i++){
            double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
            md3s.add(new Data3(square,names.get(i),monthlyContribution));
        }
        for(int j = 70;j<=170;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
                md3s.add(new Data3(square,names.get(i),monthlyContribution));
            }
        }
        square = 200;
        for(int i=0;i<names.size();i++){
            double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
            md3s.add(new Data3(square,names.get(i),monthlyContribution));
        }
        return md3s;
    }

    public List<Data3> getProvidentFirstPay(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            value.add(Float.parseFloat(housePriceList.get(i).getPrice()));
        }
        List<Data3> md3s = new ArrayList<>();
        int square = 0;
        for(int i=0;i<names.size();i++){
            double firstPay = (value.get(i))* 0.2 * square;
            md3s.add(new Data3(square,names.get(i),firstPay));
        }
        for(int j = 50;j<=90;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double firstPay = (value.get(i))* 0.2 * square;
                md3s.add(new Data3(square,names.get(i),firstPay));
            }
        }

        for(int j = 110;j<=170;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double firstPay = (value.get(i))* 0.3 * square;
                md3s.add(new Data3(square,names.get(i),firstPay));
            }
        }
        square = 200;
        for(int i=0;i<names.size();i++){
            double firstPay = (value.get(i))* 0.3 * square;
            md3s.add(new Data3(square,names.get(i),firstPay));
        }
        return md3s;
    }

    public List<Data3> getProvidentMonthlyContribution(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<MonthlyPayEntity> monthlyPayList = monthlyPayService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> valuePrice = new ArrayList<>();
        List<Float> valueWage = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            valuePrice.add(Float.parseFloat(housePriceList.get(i).getPrice()));
            valueWage.add(Float.parseFloat(monthlyPayList.get(i).getWage()));
        }
        List<Data3> md3s = new ArrayList<>();
        int square = 0;
        for(int i=0;i<names.size();i++){
            double monthlyContribution = ((valuePrice.get(i))*0.7 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
            md3s.add(new Data3(square,names.get(i),monthlyContribution));
        }
        for(int j = 50;j<=90;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double monthlyContribution = ((valuePrice.get(i))*0.7 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
                md3s.add(new Data3(square,names.get(i),monthlyContribution));
            }
        }
        for(int j = 110;j<=170;j+=20){
            square = j;
            for(int i=0;i<names.size();i++){
                double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
                md3s.add(new Data3(square,names.get(i),monthlyContribution));
            }
        }
        square = 200;
        for(int i=0;i<names.size();i++){
            double monthlyContribution = ((valuePrice.get(i))*0.8 * square * 0.049) / (12*valueWage.get(i)*(1-Math.pow(1.049,-20.0)));
            md3s.add(new Data3(square,names.get(i),monthlyContribution));
        }
        return md3s;
    }

    public List<Data4> getCommercialFirstDataList(){
        List<HousePriceEntity> housePriceList = housePriceService.findAll();
        List<Integer> names = new ArrayList<>();
        List<Float> value = new ArrayList<>();
        for(int i=0;i<housePriceList.size();i++){
            names.add(Integer.parseInt(housePriceList.get(i).getYear()));
            value.add(Float.parseFloat(housePriceList.get(i).getPrice()));
        }
        List<Data4> md4s = new ArrayList<>();
        int square = 50;
        for(int j=0;j<value.size();j++){
            if(square<=150){
                List<Double> firstPay = new ArrayList<>();
                for(int i=0;i<names.size();i++){
                    firstPay.add((value.get(i))* 0.3 * square);
                }
                md4s.add(new Data4(firstPay));
                square = square+20;
            }
        }
        return md4s;
    }
}
