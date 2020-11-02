package com.poc.outageEventAlerts.service;

import com.poc.outageEventAlerts.dto.request.OutageEventRequest;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

public interface OutageEventCreationService {
  void createAndSendEmailWithIcalAttachment(OutageEventRequest outageEventRequest);
}
