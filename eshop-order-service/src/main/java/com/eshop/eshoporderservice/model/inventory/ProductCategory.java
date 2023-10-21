package com.eshop.eshoporderservice.model.inventory;

/**
 * Lists types of product categories available for sale.
 */

public enum ProductCategory {

		HOME_APPLICANCES("HOME_APPLICANCES"), 
		LAPTOPS("LAPTOPS"), SMARTPHONES("SMARTPHONES"), ELECTRONIC_ACCESSORIES("ELECTRONIC_ACCESSORIES"), 
		SNACKS("SNACKS"), BODY_HYGIENE("BODY_HYGIENE"), CLEANING("CLEANING");

	private String category;

	private ProductCategory() {
		
	}
	
	private ProductCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
}
