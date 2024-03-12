package com.rikkei.managementuser.advice;

import com.rikkei.managementuser.exception.*;
import com.rikkei.managementuser.model.dto.ErrorResponse;
import com.rikkei.managementuser.validator.ClassUnique;
import com.rikkei.managementuser.validator.ClassValidator;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.AccessDeniedException;
import java.util.*;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid input data. Please check your email and password format.");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        Map<String, String> errorsDetails = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errorsDetails.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ vui lòng kiểm tra lại", errorsDetails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailAndPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEmailAndPhoneException(EmailAndPhoneException ex) {
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("Email","Email đã tồn tại");
        errorDetails.put("phone","Số điện thoại đã tồn tại");
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Dữ liệu không hợp lệ vui lòng kiểm tra lại", errorDetails));
    }



    @ExceptionHandler(SignInFailException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleSignInFailException(SignInFailException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NoPermissionToDelete.class)
    public ResponseEntity<ErrorResponse> handleNoPermissionToDelete(NoPermissionToDelete ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, "Bạn không có quyền xoá khóa học này", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

//    @ExceptionHandler(ClassValidator.class)
//    public ResponseEntity<ErrorResponse> handleClassUniqueException(ClassValidator classValidator){
//
//    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể truy cập vào cơ sở dự liệu", null);
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(HttpServerErrorException.InternalServerError ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi sever", null);
        return ResponseEntity.status(500).body(errorResponse);

    }

    @ExceptionHandler(PhoneUniqueException.class)
    public ResponseEntity<ErrorResponse> PhoneUniqueException(PhoneUniqueException e) {
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("phone","Số điện thoại đã tồn tại");
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Dữ liệu không hợp lệ vui lòng kiểm tra lại", errorDetails));
    }

    @ExceptionHandler(EmailUniqueException.class)
    public ResponseEntity<ErrorResponse> EmailUniqueException(EmailUniqueException e) {
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("Email","Email đã tồn tại");
        return ResponseEntity.status(400).body(new ErrorResponse(HttpStatus.BAD_REQUEST,"Dữ liệu không hợp lệ vui lòng kiểm tra lại", errorDetails));

    }





}
