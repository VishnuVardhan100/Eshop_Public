package com.eshop.eshopmodel.consumer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

/**
 * Lists types of Subscriptions user has prevailed.
 */

@Embeddable
@NoArgsConstructor
public enum UserSubscription {

	NORMAL("NORMAL"),PLUS("PLUS"),PREMIUM("PREMIUM");
	
	private String subscriptionType;

	private UserSubscription(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
	
	@Column
    public String getSubscriptionType() {
        return subscriptionType;
    }
}
