package com.poc.outageEventAlerts.controller;

import com.poc.outageEventAlerts.dto.request.OutageEventRequest;
import com.poc.outageEventAlerts.dto.response.ResponseWrapper;
import com.poc.outageEventAlerts.service.OutageEventCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@RestController
@CrossOrigin
public class OutageEventCreationController {

  @Autowired
  OutageEventCreationService outageEventCreationService;

  @PostMapping("/create")
  public ResponseWrapper getFeasibility(@RequestBody OutageEventRequest outageEventRequest) {
    outageEventCreationService.createAndSendEmailWithIcalAttachment(outageEventRequest);
    return new ResponseWrapper<>();
  }
}
