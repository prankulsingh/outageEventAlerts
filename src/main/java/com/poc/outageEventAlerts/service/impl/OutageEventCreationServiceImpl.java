package com.poc.outageEventAlerts.service.impl;

import com.poc.outageEventAlerts.adaptor.EmailAdaptor;
import com.poc.outageEventAlerts.dto.request.AttachmentDTO;
import com.poc.outageEventAlerts.dto.request.EmailRequest;
import com.poc.outageEventAlerts.dto.request.OutageEventRequest;
import com.poc.outageEventAlerts.service.OutageEventCreationService;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Prankul Singh on 01/11/20
 * @project outageEventAlerts
 */

@Service
public class OutageEventCreationServiceImpl implements OutageEventCreationService {

  @Autowired
  EmailAdaptor emailAdaptor;

  @Override
  public void createAndSendEmailWithIcalAttachment(OutageEventRequest outageEventRequest) {
    EmailRequest emailRequest = new EmailRequest(){{
      setBody(outageEventRequest.getBody());
      setSubject(outageEventRequest.getSubject());
      setToMailIds(outageEventRequest.getAllRecipientList());
      setAttachments(Collections.singletonList(new AttachmentDTO(){{
        setMimeType("text/calendar");
        setFileContent(getIcalAttachment(outageEventRequest));
        setFileName("Outage Event.ics");
      }}));
    }};

    emailAdaptor.sendEmail(emailRequest);
  }
  public byte[] getIcalAttachment(OutageEventRequest outageEventRequest) {
    // Create a TimeZone
    TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
    TimeZone timezone = registry.getTimeZone("Asia/Kolkata");
    VTimeZone tz = timezone.getVTimeZone();

    // Create the event
    String eventName = outageEventRequest.getSubject();
    DateTime start = new DateTime(outageEventRequest.getStart());
    start.setTimeZone(timezone);
    DateTime end = new DateTime(outageEventRequest.getEnd());
    end.setTimeZone(timezone);
    VEvent meeting = new VEvent(start, end, eventName);


    // add timezone info..
    meeting.getProperties().add(tz.getTimeZoneId());


    // generate unique identifier..
    String random = UUID.randomUUID().toString();
    Uid uid = new Uid(
        StringUtils.isNotBlank(outageEventRequest.getRequestId()) ?
            outageEventRequest.getRequestId() + "-" + random :
            random
    );
    meeting.getProperties().add(uid);


    //Organizer
    Organizer org1 = new Organizer(URI.create("mailto:company-noreply@company.com"));
    org1.getParameters().add(new Cn("Company"));
    meeting.getProperties().add(org1);


    // add attendees..
    if(CollectionUtils.isNotEmpty(outageEventRequest.getRequiredRecipientList())) {
      outageEventRequest.getRequiredRecipientList().forEach(attendeeEmail -> {
        Attendee attendee = new Attendee(URI.create("mailto:" + attendeeEmail));
        attendee.getParameters().add(Role.REQ_PARTICIPANT);
        attendee.getParameters().add(new Cn(attendeeEmail)); // could add person's real name here...
        meeting.getProperties().add(attendee);
      });
    }

    if(CollectionUtils.isNotEmpty(outageEventRequest.getOptionalRecipientList())) {
      outageEventRequest.getOptionalRecipientList().forEach(attendeeEmail -> {
        Attendee attendee = new Attendee(URI.create("mailto:" + attendeeEmail));
        attendee.getParameters().add(Role.OPT_PARTICIPANT);
        attendee.getParameters().add(new Cn(attendeeEmail)); // could add person's real name here...
        meeting.getProperties().add(attendee);
      });
    }


    // Create a calendar
    net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
    icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
    icsCalendar.getProperties().add(CalScale.GREGORIAN);


    // Add the event and print
    icsCalendar.getComponents().add(meeting);
    System.out.println("iCal file:");
    System.out.println(icsCalendar.toString());
    return icsCalendar.toString().getBytes();
  }
}
