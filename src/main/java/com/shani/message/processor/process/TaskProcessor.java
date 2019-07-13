package com.shani.message.processor.process;

import com.shani.message.processor.status.Status;
import com.shani.message.processor.status.StatusService;
import com.shani.message.processor.status.TaskStatus;
import com.shani.message.processor.status.TaskStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskProcessor {
    private TaskStatusRepository statusRepository;
    private StatusService statusService;
    private MessageRepository messageRepository;

    public TaskProcessor(TaskStatusRepository statusRepository, StatusService statusService, MessageRepository messageRepository) {
        this.statusRepository = statusRepository;
        this.statusService = statusService;
        this.messageRepository = messageRepository;
    }

    @Scheduled(fixedDelayString = "${processor.processTaskDelayMillis}")
    public void processTasks() {
        TaskStatus task = statusRepository.findOneByStatus(Status.Accepted.toString());
        if (task != null) {
            try {
                log.info("Processing task for messageId {}", task.getMessageId());
                statusService.updateStatus(task.getMessageId(), Status.Processing);
                processTask(task);
                statusService.updateStatus(task.getMessageId(), Status.Complete);
                log.info("Completed task for messageId {}", task.getMessageId());
            } catch (Exception e) {
                log.error("Error while processing task for messageId {} - {}", task.getMessageId(), e);
                statusService.updateStatus(task.getMessageId(), Status.Error);
            }
        }
    }

    private void processTask(TaskStatus task) {
        MessageEntity messageEntity = messageRepository.findOne(task.getMessageId());
        if (messageEntity == null) {
            throw new MessageNotFoundException("No message found or messageId " + task.getMessageId());
        }
    }

}
