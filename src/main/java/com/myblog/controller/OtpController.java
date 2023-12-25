package com.myblog.controller;

import com.myblog.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
public class OtpController {

    // used to Stores phone numbers and OTPs
    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private OtpService otpService;

    @PostMapping("/send-Otp")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        System.out.println(phoneNumber);
        // Generate Otp
        String otp = otpService.generateOtp();
        boolean sent = otpService.sendOtp("+" + phoneNumber, otp);
        System.out.print(sent);
        if (sent) {
            otpStorage.put(phoneNumber, otp);
            // Store the otp and associate it with the user in ur db
            return ResponseEntity.ok("OTP sent successfully to: " + phoneNumber);
        } else {
            return ResponseEntity.badRequest().body("Failed to send OTP");
        }
    }

    @PostMapping("/verify-Otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        // Retrieve the stored OTP for the phoneNumber from the db/HashMap
        String storedOtp = otpStorage.get(phoneNumber);
        // compare it with the submitted otp If they match, the otp is valid
        if (storedOtp != null && storedOtp.equals(otp)) {
            // Remove the OTP from storage to prevent reuse
            otpStorage.remove(phoneNumber);
            return ResponseEntity.ok("OTP is valid");  // Otp verified successfully
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
}

