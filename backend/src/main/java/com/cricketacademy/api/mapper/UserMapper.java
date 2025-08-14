package com.cricketacademy.api.mapper;

import com.cricketacademy.api.entity.User;

public class UserMapper {
    public static com.cricketacademy.api.model.User entityToModel(User entity) {
        if (entity == null)
            return null;
        com.cricketacademy.api.model.User model = new com.cricketacademy.api.model.User();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setAge(entity.getAge());
        model.setPassword(entity.getPassword());
        model.setIsActive(entity.getIsActive());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setEmailVerified(entity.getEmailVerified());
        model.setPhoneVerified(entity.getPhoneVerified());
        model.setEmailVerificationPending(entity.getEmailVerificationPending());
        model.setPhoneVerificationPending(entity.getPhoneVerificationPending());
        // Map ExperienceLevel
        if (entity.getExperienceLevel() != null) {
            try {
                model.setExperienceLevel(
                        com.cricketacademy.api.model.User.ExperienceLevel.valueOf(entity.getExperienceLevel().name()));
            } catch (IllegalArgumentException e) {
                model.setExperienceLevel(null);
            }
        }
        // Map UserRole to model's UserRole and Role
        if (entity.getRole() != null) {
            try {
                model.setUserRole(com.cricketacademy.api.model.User.UserRole.valueOf(entity.getRole().name()));
            } catch (IllegalArgumentException e) {
                model.setUserRole(null);
            }
            // Optionally map to Role if values match
            try {
                model.setRole(com.cricketacademy.api.model.User.Role.valueOf(entity.getRole().name().toLowerCase()));
            } catch (IllegalArgumentException e) {
                model.setRole(null);
            }
        }
        // Category is not present in entity, so skip
        return model;
    }

    public static User modelToEntity(com.cricketacademy.api.model.User model) {
        if (model == null)
            return null;
        User entity = new User();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setAge(model.getAge());
        entity.setPassword(model.getPassword());
        entity.setIsActive(model.getIsActive());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        entity.setEmailVerified(model.getEmailVerified());
        entity.setPhoneVerified(model.getPhoneVerified());
        entity.setEmailVerificationPending(model.getEmailVerificationPending());
        entity.setPhoneVerificationPending(model.getPhoneVerificationPending());
        // Map ExperienceLevel
        if (model.getExperienceLevel() != null) {
            try {
                entity.setExperienceLevel(User.ExperienceLevel.valueOf(model.getExperienceLevel().name()));
            } catch (IllegalArgumentException e) {
                entity.setExperienceLevel(null);
            }
        }
        // Map UserRole
        if (model.getUserRole() != null) {
            try {
                entity.setRole(User.UserRole.valueOf(model.getUserRole().name()));
            } catch (IllegalArgumentException e) {
                entity.setRole(null);
            }
        } else if (model.getRole() != null) {
            // Try mapping Role to UserRole if possible
            try {
                entity.setRole(User.UserRole.valueOf(model.getRole().name().toUpperCase()));
            } catch (IllegalArgumentException e) {
                entity.setRole(null);
            }
        }
        // Category is not present in entity, so skip
        return entity;
    }
}
