package com.netflix.logs.stats.model;


import java.util.Date;

public class LogRecordRequest {

    private String timestamp;
    private int version;
    private String message;
    private String requestDuration;
    private String sourceIp;
    private String requestMethod;
    private String requestEndpoint;
    private String requestProtocol;
    private String requestUser;
    private int responseStatus;
    private int responseSize;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(String requestDuration) {
        this.requestDuration = requestDuration;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestEndpoint() {
        return requestEndpoint;
    }

    public void setRequestEndpoint(String requestEndpoint) {
        this.requestEndpoint = requestEndpoint;
    }

    public String getRequestProtocol() {
        return requestProtocol;
    }

    public void setRequestProtocol(String requestProtocol) {
        this.requestProtocol = requestProtocol;
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    @Override
    public String toString() {
        return "LogRecordRequest{" +
                "timestamp='" + timestamp + '\'' +
                ", version=" + version +
                ", message='" + message + '\'' +
                ", requestDuration='" + requestDuration + '\'' +
                ", sourceIp='" + sourceIp + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestEndpoint='" + requestEndpoint + '\'' +
                ", requestProtocol='" + requestProtocol + '\'' +
                ", requestUser='" + requestUser + '\'' +
                ", responseStatus=" + responseStatus +
                ", responseSize=" + responseSize +
                '}';
    }
}
