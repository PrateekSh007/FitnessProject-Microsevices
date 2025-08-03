package com.fitness.activityService.dto;


import com.fitness.activityService.model.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {
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
