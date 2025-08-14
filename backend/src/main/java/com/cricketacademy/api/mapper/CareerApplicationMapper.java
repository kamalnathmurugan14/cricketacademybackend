package com.cricketacademy.api.mapper;

import com.cricketacademy.api.entity.CareerApplication;

public class CareerApplicationMapper {
    public static com.cricketacademy.api.model.CareerApplication entityToModel(CareerApplication entity) {
        if (entity == null)
            return null;
        com.cricketacademy.api.model.CareerApplication model = new com.cricketacademy.api.model.CareerApplication();
        model.setId(entity.getId());
        model.setPositionType(entity.getPositionType());
        // Map status
        if (entity.getStatus() != null) {
            switch (entity.getStatus()) {
                case PENDING:
                    model.setStatus(com.cricketacademy.api.model.CareerApplication.Status.IN_PROGRESS);
                    break;
                case APPROVED:
                    model.setStatus(com.cricketacademy.api.model.CareerApplication.Status.APPOINTED);
                    break;
                case REJECTED:
                    model.setStatus(com.cricketacademy.api.model.CareerApplication.Status.PROGRESS_NEXT);
                    break;
                default:
                    model.setStatus(null);
            }
        }
        // Map createdAt to appliedDate if possible
        if (entity.getCreatedAt() != null) {
            model.setAppliedDate(entity.getCreatedAt().toLocalDate());
        }
        // Fields not present in entity: user, formData, validatedByAdmin, notes
        return model;
    }

    public static CareerApplication modelToEntity(com.cricketacademy.api.model.CareerApplication model) {
        if (model == null)
            return null;
        CareerApplication entity = new CareerApplication();
        entity.setId(model.getId());
        entity.setPositionType(model.getPositionType());
        // Map status
        if (model.getStatus() != null) {
            switch (model.getStatus()) {
                case IN_PROGRESS:
                    entity.setStatus(CareerApplication.ApplicationStatus.PENDING);
                    break;
                case APPOINTED:
                    entity.setStatus(CareerApplication.ApplicationStatus.APPROVED);
                    break;
                case PROGRESS_NEXT:
                    entity.setStatus(CareerApplication.ApplicationStatus.REJECTED);
                    break;
                default:
                    entity.setStatus(null);
            }
        }
        // Map appliedDate to createdAt if possible
        if (model.getAppliedDate() != null) {
            entity.setCreatedAt(model.getAppliedDate().atStartOfDay());
        }
        // Fields not present in model: name, email, phone, qualifications, experience,
        // availability
        return entity;
    }
}
