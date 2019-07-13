package com.shani.message.processor.status;

import com.shani.message.processor.process.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.shani.message.processor.config.CacheConfig.STATUS_CACHE;

@Slf4j
@Service
public class StatusService {

    private TaskStatusRepository statusRepository;

    @Autowired
    public StatusService(TaskStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void updateStatus(String messageId, Status status) {
        log.info("Updating messageId {} to status {}", messageId, status);
        TaskStatus taskStatus = new TaskStatus(messageId, status);
        statusRepository.save(taskStatus);
    }

    public void saveNewMessage(MessageEntity messageEntity) {
        log.info("Save new messageId {} ", messageEntity.getMessageId());
        TaskStatus taskStatus = new TaskStatus(messageEntity.getMessageId(), Status.Accepted);
        statusRepository.save(taskStatus);
    }

    @Cacheable(STATUS_CACHE)
    public Status getStatus(String messageId) {
        log.info("Get status for messageId {}", messageId);
        TaskStatus taskStatus = statusRepository.findOne(messageId);
        if (taskStatus == null) {
            return Status.NotFound;
        }
        return taskStatus.getStatus();
    }

}
