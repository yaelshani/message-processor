package com.shani.message.processor;

import com.shani.message.processor.process.MessageTask;
import com.shani.message.processor.status.Status;
import com.shani.message.processor.status.StatusResponse;
import com.shani.message.processor.status.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/v1/messages")
public class MessageController {

    private StatusService statusService;

    @Autowired
    public MessageController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/{messageId}/status")
    public ResponseEntity<StatusResponse> getStatus(@PathVariable String messageId) {
        Status status = statusService.getStatus(messageId);
        return ResponseEntity.ok(StatusResponse.From(status));
    }

    @PostMapping()
    public Callable<ResponseEntity<MessageResponse>> processMessage(@RequestBody String message) {
        log.info("Received request to process message [{}]", message);
        return () -> {
            MessageTask task = MessageTask.createTask(message);
            statusService.updateStatus(task.getMessageId(), Status.Accepted);
            return ResponseEntity.accepted().body(MessageResponse.builder().messageId(task.getMessageId()).build());
        };
    }
}
