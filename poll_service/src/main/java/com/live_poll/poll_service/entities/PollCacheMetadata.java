package com.live_poll.poll_service.entities;


import lombok.Data;

@Data
public class PollCacheMetadata {
    private int currentIndex;
    private int total_questions;
    private long currentEndingTimestamp;
    private boolean isEnded;
}
