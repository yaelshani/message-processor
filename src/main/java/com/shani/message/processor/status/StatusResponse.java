package com.shani.message.processor.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponse {
    private String status;

    public static StatusResponse From(Status status) {
        return StatusResponse.builder().status(status.display()).build();
    }
}
