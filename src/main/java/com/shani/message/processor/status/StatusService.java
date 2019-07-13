package com.shani.message.processor.status;

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
        MessageStatus messageStatus = MessageStatus.createStatusFrom(messageId, status);
        statusRepository.save(messageStatus);
    }

    @Cacheable(STATUS_CACHE)
    public Status getStatus(String messageId) {
        log.info("Get status for messageId {}", messageId);
        MessageStatus messageStatus = statusRepository.findOne(messageId);
        if (messageStatus == null) {
            return Status.NotFound;
        }
        return messageStatus.getStatus();
    }

}
