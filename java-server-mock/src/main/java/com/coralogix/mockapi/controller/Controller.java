package com.coralogix.mockapi.controller;

import com.coralogix.mockapi.exceptions.InvalidLogEntryException;
import com.coralogix.mockapi.model.LogEntries;
import com.coralogix.mockapi.model.LogEntry;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);

    @RequestMapping(path = "/api/v1/logs", method = RequestMethod.POST)
    public ResponseEntity<Void> getLogEntry(@RequestBody LogEntries logEntries) throws InvalidLogEntryException {

        try {
            logger.info("Got log from:" + logEntries.getApplicationName());
            for (LogEntry le : logEntries.getLogEntries()) {
                logger.info("Logs: " + le.getTimestamp() + ", " + le.getSeverity() + ", " +
                        le.getText() + ", " + le.getCategory() + ", " + le.getClassName() + ", " +
                        le.getMethodName() + ", " + le.getThreadId());
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new InvalidLogEntryException("Log Entry is invalid");
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
