package com.fitness.activityService.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Activity {
    private Long id;
    private Long userId;
    private ActivityType activity ;
    private Integer duration ;
    private Integer caloriesBurned ;
    private LocalDateTime startTime ;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;
}
