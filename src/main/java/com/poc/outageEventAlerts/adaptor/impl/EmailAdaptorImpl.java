package com.poc.outageEventAlerts.adaptor.impl;

import com.poc.outageEventAlerts.adaptor.EmailAdaptor;
import com.poc.outageEventAlerts.dto.request.EmailRequest;
import com.poc.outageEventAlerts.dto.response.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@Service
public class EmailAdaptorImpl implements EmailAdaptor {

  @Value("${email.api}")
  private String emailApi;
  @Value("${email.api.authorization:null}")
  private String emailApiAuthorization;
  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void sendEmail(EmailRequest emailRequest) {
    try {
      HttpHeaders httpHeaders = null;
      if (!emailApiAuthorization.equalsIgnoreCase("null")) {
        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, emailApiAuthorization);
      }

      HttpEntity<EmailRequest> request = new HttpEntity<>(emailRequest, httpHeaders);

      ResponseEntity<ResponseWrapper> responseEntity = restTemplate.postForEntity(
          emailApi,
          request,
          ResponseWrapper.class,
          Collections.EMPTY_MAP
      );

      ResponseWrapper response;
      if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
        response = responseEntity.getBody();
      } else {
        throw new RuntimeException("Email downstream failed. Status code: " + responseEntity.getStatusCode() +
            " and body: " + responseEntity.getBody());
      }

      if (response == null || !response.isSuccess()) {
        String errorMessage = (response == null) ?
            "Email downstream response is null!" :
            "Email downstream failed because: " + response.getErrorMsg();
        throw new RuntimeException(errorMessage);
      }
    } catch (Exception e) {
      throw e;
    }
  }
}
