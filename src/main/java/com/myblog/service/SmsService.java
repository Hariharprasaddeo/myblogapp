package com.myblog.service;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SmsService {

    @Value("${twilio.accountSID}")
    private String accountSID;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.twilioPhoneNo}")
    private String twilioPhoneNo;

    public void sendSms(String to, String message){
        System.out.println(accountSID);
        System.out.println(authToken);
        Twilio.init(accountSID, authToken);

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNo),  // your Twilio Phone No.
                message
        ).create();
    }
}
