package com.itguigu.springcloud.controller;


import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.itguigu.springcloud.service.PaymentHystrixService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback ="payment_Global_FallbackMethod")
public class OderHystirxController {

    @Resource
    private PaymentHystrixService paymentHystrixService;
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result=paymentHystrixService.paymentInfo_OK(id);
        return result;
    }
//    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        String result=paymentHystrixService.paymentInfo_TimeOut(id);
//        return result;
//    }
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "15000")
//    })
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable ("id") Integer id){
        //int time=3;
//        try{
//            TimeUnit.SECONDS.sleep(time);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
        int age=10/0;
        String result=paymentHystrixService.paymentInfo_TimeOut(id);

        return result+ "线程池   "+Thread.currentThread().getName()+"pay menInfo_TimeOut,id"+id+"\t"+"😀--"+"耗时";
    }
    public String paymentInfo_TimeOutHandler(Integer id){
        return "我是消费者80，对方繁忙再试"+Thread.currentThread().getName()+"/(ㄒoㄒ)/~~,id"+id+"handler";
    }

    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，//（TOT）/";
    }




}
