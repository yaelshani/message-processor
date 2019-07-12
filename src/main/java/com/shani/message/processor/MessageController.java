package com.shani.message.processor;

import com.shani.message.processor.status.MessageStatus;
import com.shani.message.processor.status.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/message")
public class MessageController {

    @GetMapping("/{messageId}/status")
    public ResponseEntity<MessageStatus> getStatus(@PathVariable String messageId) {
        return ResponseEntity.ok(MessageStatus.builder().status(Status.NotFound.display()).build());

    }
}
