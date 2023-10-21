package com.eshop.eshopservice.helper;

import java.util.stream.Stream;

import com.eshop.eshopmodel.customer.CustomerSubscription;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Subscription Type Enumeration Converter for Database Column and Entity Attribute
 */

@Converter(autoApply = true)
public class CustomerSubscriptionConverter implements AttributeConverter<CustomerSubscription, String> {
 
    @Override
    public String convertToDatabaseColumn(CustomerSubscription subscriptionType) {
        if (subscriptionType == null) {
            return null;
        }
        return subscriptionType.getSubscriptionType();
    }

    @Override
    public CustomerSubscription convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(CustomerSubscription.values())
          .filter(c -> c.getSubscriptionType().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}