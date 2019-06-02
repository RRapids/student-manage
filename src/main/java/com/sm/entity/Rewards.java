package com.sm.entity;

import java.util.Date;

public class Rewards {
    private String id;
    private String type;
    private Date rewardsDate;
    private String number;
    private String name;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getRewardsDate() {
        return rewardsDate;
    }

    public void setRewardsDate(Date rewardsDate) {
        this.rewardsDate = rewardsDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Rewards{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", rewardsDate=" + rewardsDate +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
