package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequest;
import com.fitness.activityService.dto.ActivityResponse;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserValidationService userValidationService ;

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.exchange.key}d ")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {

        boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId()) ;
        if(!isValidUser){
            throw new RuntimeException("Invalid user:" + activityRequest.getUserId()) ;
        }

        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .activity(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .build() ;

        Activity savedActivity = activityRepository.save(activity) ;

        //publish to rabbitmq for aiProcessing
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            log.error("failed to publish activity to RabbitMq:" ,e) ;
        }


        return getActivityResponse(savedActivity);

    }

    private ActivityResponse getActivityResponse(Activity savedActivity) {
        ActivityResponse activityResponse =  new ActivityResponse() ;
        activityResponse.setUserId(savedActivity.getUserId());
        activityResponse.setId(String.valueOf(savedActivity.getId()));
        activityResponse.setActivity(savedActivity.getActivity());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setStartTime(savedActivity.getStartTime());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setUpdatedAt(savedActivity.getUpdatedAt());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        return activityResponse;
    }

    public List<ActivityResponse> getUserByActivity(Long userId) {
        List<Activity> activities = activityRepository.findByUserId((userId));
        return activities.stream()
                .map(this::getActivityResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String id) {
        return activityRepository.findById(id) ;
    }
}

