package com.poc.outageEventAlerts.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@Data
@NoArgsConstructor
public class EmailRequest {

  private List<String> toMailIds = new ArrayList<>();
  private String subject = "";
  private String body = "";
  private String source = "Outage Alerts POC";
  private List<AttachmentDTO> attachments;

}
