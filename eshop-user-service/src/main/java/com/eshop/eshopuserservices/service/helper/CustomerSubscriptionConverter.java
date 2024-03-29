package com.eshop.eshopuserservices.service.helper;

import java.util.stream.Stream;

import com.eshop.eshopuserservice.model.customer.CustomerSubscription;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Subscription Type Enumeration Converter for Database Column and Entity Attribute
 */

@Converter(autoApply = true)
public class CustomerSubscriptionConverter implements AttributeConverter<CustomerSubscription, String> {
 
    @Override
    public String convertToDatabaseColumn(CustomerSubscription customerSubscription) {
        if (customerSubscription == null) {
            return null;
        }
        return customerSubscription.getSubscriptionType();
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