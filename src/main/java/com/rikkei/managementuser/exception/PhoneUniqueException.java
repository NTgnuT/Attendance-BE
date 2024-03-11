package com.rikkei.managementuser.exception;

import com.rikkei.managementuser.validator.PhoneNumberUnique;

public class PhoneUniqueException extends Exception{
    public PhoneUniqueException(String mess){
        super(mess);
    }
}
