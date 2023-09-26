package com.eshop.eshopservice.manipulator;

import com.eshop.eshopmodel.consumer.UserSubscription;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.*;

@Converter(autoApply = true)
public class UserSubscriptionConverter implements AttributeConverter<UserSubscription, String> {
 
    @Override
    public String convertToDatabaseColumn(UserSubscription category) {
        if (category == null) {
            return null;
        }
        return category.getSubscriptionType();
    }

    @Override
    public UserSubscription convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(UserSubscription.values())
          .filter(c -> c.getSubscriptionType().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}