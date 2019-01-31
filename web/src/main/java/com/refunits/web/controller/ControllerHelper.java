package com.refunits.web.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHelper {

    @ExceptionHandler(Exception.class)
    public String checkException(Exception ex) {

        return "error";
    }
}