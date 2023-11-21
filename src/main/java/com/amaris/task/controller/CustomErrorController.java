package com.amaris.task.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        // Your error handling logic here
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return new ResponseEntity<>("Error code: " + statusCode + ", Exception: " + exception, HttpStatus.valueOf(statusCode));
    }
}
