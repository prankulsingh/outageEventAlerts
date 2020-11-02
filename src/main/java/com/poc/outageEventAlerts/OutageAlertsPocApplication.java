package com.poc.outageEventAlerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OutageAlertsPocApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(OutageAlertsPocApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


/*

References:

https://ical4j.github.io/ical4j-user-guide/examples/
https://github.com/ical4j/ical4j

 */