package com.shani.message.processor.process;

import com.shani.message.processor.status.MessageStatus;
import com.shani.message.processor.status.Status;
import com.shani.message.processor.status.StatusService;
import com.shani.message.processor.status.TaskStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TaskProcessor {
    private TaskStatusRepository statusRepository;
    private StatusService statusService;

    public TaskProcessor(TaskStatusRepository statusRepository, StatusService statusService) {
        this.statusRepository = statusRepository;
        this.statusService = statusService;
    }

    @Scheduled(fixedDelayString = "${processor.processTaskDelayMillis}")
    public void processTasks() {
        List<MessageStatus> tasks = statusRepository.findAllByStatus(Status.Accepted.toString());
        for (MessageStatus task : tasks) {
            try {
                log.info("Processing task for messageId {}", task.getMessageId());
                statusService.updateStatus(task.getMessageId(), Status.Processing);
                statusService.updateStatus(task.getMessageId(), Status.Complete);
                log.info("Completed task for messageId {}", task.getMessageId());
            } catch (Exception e) {
                log.error("Error while processing task for messageId {} - {}", task.getMessageId(), e);
                statusService.updateStatus(task.getMessageId(), Status.Error);
            }
        }
    }
}
