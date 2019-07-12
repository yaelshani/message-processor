package com.shani.message.processor.status;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskStatusRepository extends MongoRepository<MessageStatus, String> {
    List<MessageStatus> findAllByStatus(String status);
}
