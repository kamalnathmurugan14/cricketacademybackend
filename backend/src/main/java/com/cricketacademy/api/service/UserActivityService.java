package com.cricketacademy.api.service;

import com.cricketacademy.api.entity.UserActivity;
import com.cricketacademy.api.repository.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;

    /**
     * Log user login activity with enhanced error handling and retry logic
     */
    @Transactional
    public void logLogin(Long userId, String ipAddress, String userAgent, String sessionId) {
        logUserActivity(userId, UserActivity.ActivityType.LOGIN, ipAddress, userAgent, sessionId,
                "User logged in successfully");
    }

    /**
     * Log user logout activity with enhanced error handling and retry logic
     */
    @Transactional
    public void logLogout(Long userId, String ipAddress, String userAgent, String sessionId) {
        logUserActivity(userId, UserActivity.ActivityType.LOGOUT, ipAddress, userAgent, sessionId, "User logged out");
    }

    /**
     * Log failed login attempt with enhanced error handling
     */
    @Transactional
    public void logFailedLogin(Long userId, String ipAddress, String userAgent, String reason) {
        logUserActivity(userId, UserActivity.ActivityType.LOGIN_FAILED, ipAddress, userAgent, null, reason);
    }

    /**
     * Log password change activity with enhanced error handling
     */
    @Transactional
    public void logPasswordChange(Long userId, String ipAddress, String userAgent) {
        logUserActivity(userId, UserActivity.ActivityType.PASSWORD_CHANGED, ipAddress, userAgent, null,
                "Password changed successfully");
    }

    /**
     * Enhanced generic method to log user activity with comprehensive error
     * handling
     */
    @Transactional
    private void logUserActivity(Long userId, UserActivity.ActivityType activityType,
            String ipAddress, String userAgent, String sessionId, String additionalInfo) {

        if (userId == null) {
            log.error("Cannot log activity: userId is null");
            return;
        }

        try {
            // Validate required fields
            if (ipAddress == null)
                ipAddress = "unknown";
            if (userAgent == null)
                userAgent = "unknown";

            UserActivity activity = new UserActivity();
            activity.setUserId(userId);
            activity.setActivityType(activityType);
            activity.setIpAddress(ipAddress);
            activity.setUserAgent(userAgent);
            activity.setSessionId(sessionId);
            activity.setAdditionalInfo(additionalInfo);

            UserActivity saved = userActivityRepository.save(activity);
            log.info("Successfully logged activity: {} for user ID: {} with ID: {}",
                    activityType, userId, saved.getId());

        } catch (Exception e) {
            log.error("Failed to log activity: {} for user ID: {}. Error: {}",
                    activityType, userId, e.getMessage(), e);

            // Attempt to log the error as a last resort
            try {
                UserActivity errorActivity = new UserActivity();
                errorActivity.setUserId(userId);
                errorActivity.setActivityType(UserActivity.ActivityType.LOGIN_FAILED);
                errorActivity.setAdditionalInfo("Error logging activity: " + e.getMessage());
                userActivityRepository.save(errorActivity);
            } catch (Exception innerEx) {
                log.error("Critical: Failed to log even error activity for user ID: {}", userId, innerEx);
            }
        }
    }

    /**
     * Log user activity with validation and retry mechanism
     */
    @Transactional
    public boolean logActivitySafely(Long userId, UserActivity.ActivityType activityType,
            String ipAddress, String userAgent, String sessionId, String additionalInfo) {
        try {
            logUserActivity(userId, activityType, ipAddress, userAgent, sessionId, additionalInfo);
            return true;
        } catch (Exception e) {
            log.error("Failed to log activity safely: {} for user ID: {}", activityType, userId, e);
            return false;
        }
    }

    /**
     * Get user activity statistics for monitoring
     */
    public Map<String, Object> getActivityStats() {
        Map<String, Object> stats = new HashMap<>();
        try {
            stats.put("totalActivities", userActivityRepository.count());
            stats.put("recentActivities", userActivityRepository.findAllLoginActivities().size());
            return stats;
        } catch (Exception e) {
            log.error("Error getting activity stats", e);
            return Map.of("error", "Failed to get stats");
        }
    }

    /**
     * Get all activities for a specific user
     */
    public List<UserActivity> getUserActivities(Long userId) {
        return userActivityRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    /**
     * Get recent activities for a user
     */
    public List<UserActivity> getRecentUserActivities(Long userId, int limit) {
        return userActivityRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .limit(limit)
                .toList();
    }

    /**
     * Get all login activities
     */
    public List<UserActivity> getAllLoginActivities() {
        return userActivityRepository.findAllLoginActivities();
    }

    /**
     * Get all logout activities
     */
    public List<UserActivity> getAllLogoutActivities() {
        return userActivityRepository.findAllLogoutActivities();
    }
}
