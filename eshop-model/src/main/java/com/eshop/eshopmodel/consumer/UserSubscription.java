package com.eshop.eshopmodel.consumer;

import jakarta.persistence.Embeddable;

/**
 * Lists types of Subscriptions user has prevailed.
 */

@Embeddable
public enum UserSubscription {

	NORMAL,PLUS,PREMIUM;
	
}
