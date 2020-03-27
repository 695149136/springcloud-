package com.itguigu.springcloud.controller;


import com.itguigu.springcloud.entities.CommonResult;
import com.itguigu.springcloud.entities.Payment;
import com.itguigu.springcloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@RestController
@Slf4j
    public class PaymentController {


    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;
    @PostMapping(value = "/payment/create")
    public CommonResult crete(@RequestBody Payment payment){

        int r=paymentService.create(payment);
        Log.getLog("","",r);
        if(r>0){
        return new CommonResult(200,"插入成功"+serverPort);
        }else{
            return new CommonResult(440,"失败");
        }
    }
    @GetMapping(value = "/payment/get/{id}",produces = { "application/json;charset=UTF-8" })
    public CommonResult getPaymentById(@PathVariable("id")  Long id){

        Payment r=paymentService.getPaymentById(id);

        if(r!=null){
            return new CommonResult(200,"查询成功0"+serverPort,r);
        }else{
            return new CommonResult(440,"失败,id为"+id);
        }
    }
    @GetMapping(value="/payment/discovery")
    public Object discovery(){
        List<String> services=discoveryClient.getServices();
        for(String element: services){
            Logger.getLogger("element"+element);
        }
        List<ServiceInstance> instance= discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance element: instance){
            Logger.getLogger(element.getServiceId()+"t"+element.getHost()+"t"+element.getPort()+"t"+element.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value="/payment/lb")
    public  String getServerPort(){
        return serverPort;
    }
}
