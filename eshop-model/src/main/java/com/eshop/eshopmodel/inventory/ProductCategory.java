package com.eshop.eshopmodel.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

/**
 * Lists types of product categories available for sale.
 */

@Embeddable
@NoArgsConstructor
public enum ProductCategory {

		HOME_APPLICANCES("HOME_APPLICANCES"), 
		LAPTOPS("LAPTOPS"), SMARTPHONES("SMARTPHONES"), ELECTRONIC_ACCESSORIES("ELECTRONIC_ACCESSORIES"), 
		SNACKS("SNACKS"), BODY_HYGIENE("BODY_HYGIENE"), CLEANING("CLEANING");

	private String category;
	
	private ProductCategory(String category) {
		this.category = category;
	}
	
	@Column
	public String getCategory() {
		return category;
	}
}
