package com.myblog.controller;

import com.myblog.dto.SmsRequest;
import com.myblog.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/sendSms")
    public void sendSms(@RequestBody SmsRequest smsRequest){
        smsService.sendSms(smsRequest.getTo(),smsRequest.getMessage());
    }

}
