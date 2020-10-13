package com.coralogix.mockapi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "privateKey",
        "applicationName",
        "subsystemName",
        "computerName",
        "logEntries"
})
public class LogEntries {

    @JsonProperty("privateKey")
    private UUID privateKey;
    @JsonProperty("applicationName")
    private String applicationName;
    @JsonProperty("subsystemName")
    private String subsystemName;
    @JsonProperty("computerName")
    private String computerName;
    @JsonProperty("logEntries")
    private List<LogEntry> logEntries = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("privateKey")
    public UUID getPrivateKey() {
        return privateKey;
    }

    @JsonProperty("privateKey")
    public void setPrivateKey(UUID privateKey) {
        this.privateKey = privateKey;
    }

    @JsonProperty("applicationName")
    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty("applicationName")
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @JsonProperty("subsystemName")
    public String getSubsystemName() {
        return subsystemName;
    }

    @JsonProperty("subsystemName")
    public void setSubsystemName(String subsystemName) {
        this.subsystemName = subsystemName;
    }

    @JsonProperty("computerName")
    public String getComputerName() {
        return computerName;
    }

    @JsonProperty("computerName")
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    @JsonProperty("logEntries")
    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    @JsonProperty("logEntries")
    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}