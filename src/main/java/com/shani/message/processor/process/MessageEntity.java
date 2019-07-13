package com.shani.message.processor.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class MessageEntity {

    @Id
    private String messageId;
    private String message;

    public MessageEntity(String message) {
        this.message = message;
        this.messageId = UUID.randomUUID().toString();
    }
}
