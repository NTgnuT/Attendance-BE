package com.rikkei.managementuser.exception;

import org.springframework.stereotype.Component;

public class EmailAndPhoneException extends Exception{
    public EmailAndPhoneException (String s){
        super(s);
    }
}
