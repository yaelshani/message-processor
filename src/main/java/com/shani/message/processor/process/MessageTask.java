package com.shani.message.processor.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTask {
    private String messageId;
    private String message;

    public static MessageTask createTask(String message) {
        return MessageTask.builder()
                .message(message)
                .messageId(UUID.randomUUID().toString())
                .build();
    }
}
