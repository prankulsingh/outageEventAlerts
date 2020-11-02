package com.poc.outageEventAlerts.controller.errorHandler;

import com.poc.outageEventAlerts.dto.response.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ResponseWrapper> handleSFDCException(Exception e) {
    ResponseWrapper apiResponse = new ResponseWrapper<>(false, e.getMessage(), null);
    e.printStackTrace();
    return ResponseEntity.accepted().body(apiResponse);
  }
}
