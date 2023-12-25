package com.myblog.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.accountSID}")
    private String twilioAccountSid;
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.twilioPhoneNo}")
    private String twilioPhoneNo;

    public String generateOtp() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }

    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(twilioAccountSid, twilioAuthToken);   // it will check whether user is authenticated or not
        try {
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioPhoneNo),
                    "Your OTP is: " + otp
            ).create();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

