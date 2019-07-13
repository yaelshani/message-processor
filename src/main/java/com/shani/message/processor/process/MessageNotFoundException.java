package com.shani.message.processor.process;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String msg) {
        super(msg);
    }
}
