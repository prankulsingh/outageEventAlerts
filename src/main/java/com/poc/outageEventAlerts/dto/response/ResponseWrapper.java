package com.poc.outageEventAlerts.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {
  boolean success = true;
  String errorMsg = null;
  T result = null;
}
