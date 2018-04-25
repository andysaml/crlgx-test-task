package com.coralogix.mockapi.controller;

import com.coralogix.mockapi.exceptions.InvalidLogEntryException;
import com.coralogix.mockapi.model.LogEntries;
import com.coralogix.mockapi.model.LogEntry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller {

    @RequestMapping(path = "/api/v1/logs", method = RequestMethod.POST)
    public ResponseEntity<Void> getLogEntry(@RequestBody LogEntries logEntries) throws InvalidLogEntryException {

        if (logEntries.getPrivateKey() instanceof UUID)
            System.out.println("Got new logs: UUID - ok, app name: " + logEntries.getApplicationName());
        else {
            throw new InvalidLogEntryException("LogEntry UUID is wrong");
        }
        for (LogEntry le : logEntries.getLogEntries() ) {
            System.out.println(le.getTimestamp() + ", " + le.getSeverity() + ", " +
                    le.getText() + ", " + le.getCategory() + ", " + le.getClassName() + ", " +
                    le.getMethodName()+ ", " + le.getThreadId());
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
