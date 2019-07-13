package com.shani.message.processor.status;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskStatusRepository extends MongoRepository<TaskStatus, String> {
    TaskStatus findOneByStatus(String status);
}
