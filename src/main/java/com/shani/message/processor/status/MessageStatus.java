package com.shani.message.processor.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message-status")
public class MessageStatus {

    @Id
    private String messageId;
    private Status status;
    private Long timestamp;

    public static MessageStatus createStatusFrom(String messageId, Status status) {
        return MessageStatus.builder().messageId(messageId).status(status).timestamp(System.currentTimeMillis()).build();
    }
}
