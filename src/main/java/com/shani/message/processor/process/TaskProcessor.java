package com.shani.message.processor.process;

import com.shani.message.processor.status.MessageStatus;
import com.shani.message.processor.status.Status;
import com.shani.message.processor.status.StatusService;
import com.shani.message.processor.status.TaskStatusRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskProcessor {
    private TaskStatusRepository statusRepository;
    private StatusService statusService;

    public TaskProcessor(TaskStatusRepository statusRepository, StatusService statusService) {
        this.statusRepository = statusRepository;
        this.statusService = statusService;
    }

    @Scheduled(fixedDelay = 10)//TODO: application.yml
    public void processTasks() {
        List<MessageStatus> tasks = statusRepository.findAllByStatus(Status.Accepted.toString());
        for (MessageStatus task : tasks) {
            try {
                statusService.updateStatus(task.getMessageId(), Status.Processing);
                statusService.updateStatus(task.getMessageId(), Status.Complete);
            } catch (Exception e) {
                statusService.updateStatus(task.getMessageId(), Status.Error);
            }
        }
    }
}
