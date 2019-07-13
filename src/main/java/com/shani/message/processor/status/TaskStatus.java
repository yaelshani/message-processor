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
@Document(collection = "tasks")
public class TaskStatus {

    @Id
    private String messageId;
    private Status status;
    private Long timestamp;

    public TaskStatus(String messageId, Status status) {
        this.messageId = messageId;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}
