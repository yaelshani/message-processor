package com.shani.message.processor.status;

public enum Status {
    NotFound("Not-Found"),
    Accepted("Accepted"),
    Processing("Processing"),
    Complete("Complete"),
    Error("Error");

    private String display;

    Status(String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
