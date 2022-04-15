package com.itsol.recruit_managerment.dto;
public class ErrorMessage {

    private String userMessage;

    private String internalMessage;

    private int code;

    public ErrorMessage() {
    }

    public ErrorMessage(String userMessage, String internalMessage, int code) {
        this.userMessage = userMessage;
        this.internalMessage = internalMessage;
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public void setInternalMessage(String internalMessage) {
        this.internalMessage = internalMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}