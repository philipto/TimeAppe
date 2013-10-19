package com.philiptorchinsky.TimeAppe;

import java.sql.Time;

/**
 * Created by filip on 25.07.13.
 */
public class Item {

    String project;
    String status;
    long secondsSpent;
    long lastactivated;

    public long getLastactivated() {
        return lastactivated;
    }

    public void setLastactivated(long lastactivated) {
        this.lastactivated = lastactivated;
    }

    public Item(String project, String status, long secondsSpent, long lastactivated) {
        this.project = project;
        this.status = status;
        this.secondsSpent = secondsSpent;
        this.lastactivated = lastactivated;

    }

    public long getSecondsSpent() {
        return secondsSpent;
    }

    public void setSecondsSpent(long secondsSpent) {
        this.secondsSpent = secondsSpent;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    }
