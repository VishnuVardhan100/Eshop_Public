package com.eshop.eshopmodel.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

/**
 * Lists types of Subscriptions customer has prevailed.
 */

@Embeddable
@NoArgsConstructor
public enum CustomerSubscription {

	NORMAL("NORMAL"),PLUS("PLUS"),PREMIUM("PREMIUM");
	
	private String subscriptionType;

	private CustomerSubscription(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
	
	@Column
    public String getSubscriptionType() {
        return subscriptionType;
    }
}
