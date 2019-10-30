package com.example.mqttdash.Model;

public class ItemModel {

    private String device;
    private String time;
    private String topic;
    private int type;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public ItemModel(String device, String time, int type,String topic) {
        this.device = device;
        this.time = time;
        this.type = type;
        this.topic = topic;
    }
}
