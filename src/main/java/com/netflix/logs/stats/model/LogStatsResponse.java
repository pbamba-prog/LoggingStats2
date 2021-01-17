package com.netflix.logs.stats.model;

public class LogStatsResponse {

    private String requestEndpoint;
    private String user;
    private Long successRatio;
    private Integer avgRequestDuration;

    public String getRequestEndpoint() {
        return requestEndpoint;
    }

    public void setRequestEndpoint(String requestEndpoint) {
        this.requestEndpoint = requestEndpoint;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



    public Long getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(Long successRatio) {
        this.successRatio = successRatio;
    }

    public Integer getAvgRequestDuration() {
        return avgRequestDuration;
    }

    public void setAvgRequestDuration(Integer avgRequestDuration) {
        this.avgRequestDuration = avgRequestDuration;
    }

    @Override
    public String toString() {
        return "LogStatsResponse{" +
                "requestEndpoint='" + requestEndpoint + '\'' +
                ", user='" + user + '\'' +
                ", successRatio=" + successRatio +
                ", avgRequestDuration=" + avgRequestDuration +
                '}';
    }
}
