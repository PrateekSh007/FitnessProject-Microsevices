package com.fitness.aiService.service;

import com.fitness.aiService.model.Recommendation;
import com.fitness.aiService.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository ;

    public List<Recommendation> getUserRecommendation(Long userId) {
        return recommendationRepository.findByUserId(userId) ;
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No Recommendation found dot this activity:" + activityId)) ;
    }
}
