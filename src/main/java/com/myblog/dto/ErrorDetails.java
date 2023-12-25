package com.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
@Getter
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

//    public ErrorDetails(Date timestamp, String message, String details) {   // constructor
//        this.timestamp = timestamp;
//        this.message = message;
//        this.details = details;
//    }
}