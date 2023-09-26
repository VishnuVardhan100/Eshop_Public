package com.eshop.eshopservice.manipulator;

import com.eshop.eshopmodel.consumer.UserSubscription;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.*;

/**
 * Subscription Type Enumeration Converter for Database Column and Entity Attribute
 */

@Converter(autoApply = true)
public class UserSubscriptionConverter implements AttributeConverter<UserSubscription, String> {
 
    @Override
    public String convertToDatabaseColumn(UserSubscription subscriptionType) {
        if (subscriptionType == null) {
            return null;
        }
        return subscriptionType.getSubscriptionType();
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