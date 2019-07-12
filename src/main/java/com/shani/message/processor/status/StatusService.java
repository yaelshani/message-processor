package com.shani.message.processor.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private TaskStatusRepository statusRepository;

    @Autowired
    public StatusService(TaskStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void updateStatus(String messageId, Status status) {
        MessageStatus messageStatus = MessageStatus.createStatusFrom(messageId, status);
        statusRepository.save(messageStatus);
    }

    public Status getStatus(String messageId) {
        MessageStatus messageStatus = statusRepository.findOne(messageId);
        if (messageStatus == null) {
            return Status.NotFound;
        }
        return messageStatus.getStatus();
    }

}
