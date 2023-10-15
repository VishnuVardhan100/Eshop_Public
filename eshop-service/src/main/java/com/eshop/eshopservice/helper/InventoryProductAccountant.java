package com.eshop.eshopservice.manipulator;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.inventory.InventoryProduct;
import com.eshop.eshopmodel.logistics.OrderProduct;
import com.eshop.eshoprepository.InventoryProductRepository;
import com.eshop.exception.InventoryProductException;

/**
 * The Inventory Product Accountant performs the following functions:
 * 	-Check if each among list of products have required quantity in inventory to warrant a purchase
 * 	-Adjust the inventory quantity per requirement from the order
 */

@Service
public class InventoryProductAccountant {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InventoryProductRepository inventoryProductRepository;

	private int size = 0;
	
	private InventoryProduct inventoryRetrieveProduct = null;
	
	private Iterator<Long> inventoryPrdIDListIterator = null;
	
	private OrderProduct orderProduct = null;
	
	private Iterator<OrderProduct> orderPrdListIterator = null;
	
	private long existingQuantity = 0; 
	
	private long requestedNumberOfitems = 0;
	
	
	/**
	 * Performs various checks on both lists to see if concurrent order items and Inventory product IDs match 
	 * @param inventoryProductID List
	 * @param orderProduct List
	 * @throws InventoryProductException
	 */
	public void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList)
		throws InventoryProductException {

		size = inventoryProductIDList.size();
		
		// Check if size of both lists matches
		if(size != orderProductList.size()) {
			throw new InventoryProductException(messageSource.
					getMessage("InventoryProductIDListAndOrderProductListSizeMismatch", null, LocaleContextHolder.getLocale()));
		}

		inventoryPrdIDListIterator = inventoryProductIDList.iterator();
		orderPrdListIterator = orderProductList.iterator();
		
		// Perform inventoryProduct availability check, quantity check 
		// and adjust inventoryProductQuantity if acceptable
		while((size > 0)) {			
			//Retrieve inventory Product
			inventoryRetrieveProduct = inventoryProductRepository.findById(inventoryPrdIDListIterator.next()).
					orElseThrow(() -> 
					new InventoryProductException(messageSource.getMessage("InventoryProductNotFound", null, LocaleContextHolder.getLocale())));
			orderProduct = orderPrdListIterator.next();
			
			//Check if both inventory Product ID and order Product in list are referring to same item
			if(!inventoryRetrieveProduct.getInventoryProductName().equalsIgnoreCase(orderProduct.getOrderProductName())) {
				throw new InventoryProductException(messageSource.
						getMessage("InventoryProductOrderProductMismatch", null, LocaleContextHolder.getLocale()));
			}
			
			//Check if inventory Product quantity is sufficient to warrant a purchase
			existingQuantity = inventoryRetrieveProduct.getInventoryProductQuantity();
			requestedNumberOfitems = orderProduct.getOrderProductQuantity();
			if(existingQuantity < requestedNumberOfitems) {
				throw new InventoryProductException(messageSource.
						getMessage("InventoryProductQuantityInsufficent", null, LocaleContextHolder.getLocale()));
			}
			else {
				//Perform Inventory Product Quantity Adjustment
				adjustInventoryProdutQuantity(existingQuantity, requestedNumberOfitems, inventoryRetrieveProduct);
			}
			
			existingQuantity = 0;
			requestedNumberOfitems = 0;
			inventoryRetrieveProduct = null;
			orderProduct = null;
			
			size = size - 1;
		}
	}
	
	/**
	 * Performs adjustment of the Inventory Product Quantity after all checks and persists info to database
	 * @param existingQuantity
	 * @param requestedNumberOfitems
	 * @param inventoryRetrieveProduct
	 */
	private void adjustInventoryProdutQuantity(long existingQuantity, long requestedNumberOfitems,
		InventoryProduct inventoryRetrieveProduct) {
		long newQuantity = existingQuantity - requestedNumberOfitems;
		inventoryRetrieveProduct.setInventoryProductQuantity(newQuantity);
		inventoryProductRepository.save(inventoryRetrieveProduct);
	}
	
}
