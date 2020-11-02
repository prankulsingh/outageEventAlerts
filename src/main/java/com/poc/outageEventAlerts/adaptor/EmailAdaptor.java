package com.poc.outageEventAlerts.adaptor;

import com.poc.outageEventAlerts.dto.request.EmailRequest;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */
public interface EmailAdaptor {
  void sendEmail(EmailRequest emailRequest);
}
