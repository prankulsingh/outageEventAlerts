package com.poc.outageEventAlerts.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutageEventRequest {
  String requestId;
  List<String> requiredRecipientList;
  List<String> optionalRecipientList;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a", timezone="IST")
  Date start;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a", timezone="IST")
  Date end;
  String subject;
  String body;

  public List<String> getAllRecipientList() {
    List<String> toEmailIds = new ArrayList<>();
    if(CollectionUtils.isNotEmpty(this.requiredRecipientList)) {
      toEmailIds.addAll(this.requiredRecipientList);
    }
    if(CollectionUtils.isNotEmpty(this.optionalRecipientList)) {
      toEmailIds.addAll(this.optionalRecipientList);
    }

    return toEmailIds;
  }
}
