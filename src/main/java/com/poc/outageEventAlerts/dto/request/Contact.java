package com.poc.outageEventAlerts.dto.request;

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
public class Contact {
  String email;
  String displayName;
}
