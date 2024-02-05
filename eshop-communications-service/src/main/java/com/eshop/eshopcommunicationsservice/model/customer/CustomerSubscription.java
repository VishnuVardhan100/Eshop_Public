package com.eshop.eshopcommunicationsservice.model.customer;

import java.io.Serializable;

/**
 * Lists types of Subscriptions customer has prevailed.
 */

public enum CustomerSubscription implements Serializable{

	NORMAL("NORMAL"),PLUS("PLUS"),PREMIUM("PREMIUM");
	
	private String subscriptionType;

	CustomerSubscription() {
		
	}
	
	CustomerSubscription(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
	
    public String getSubscriptionType() {
        return subscriptionType;
    }
}
