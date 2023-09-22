package com.eshop.eshopmodel.inventory;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ProductCategory {

		HOME_APPLICANCES, 
		LAPTOPS, SMARTPHONES, ELECTRONIC_ACCESSORIES, 
		SNACKS_CHIPS, SNACKS_COOKIES,
		BODY_HYGIENE, CLEANING
		;
}
