package com.shani.message.processor.status;

public enum Status {
    NotFound("Not-Found");

    private String display;

    Status(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
