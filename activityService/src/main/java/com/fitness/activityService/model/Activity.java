package com.fitness.activityService.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collation = "activities")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
    @Id
    private Long id;
    private Long userId;
    private ActivityType activity ;
    private Integer duration ;
    private Integer caloriesBurned ;
    private LocalDateTime startTime ;

    @Field("metrics")
    private Map<String, Object> additionalMetrics;
    @CreatedDate
    private LocalDateTime createdAt ;
    @LastModifiedDate
    private LocalDateTime updatedAt ;
}
