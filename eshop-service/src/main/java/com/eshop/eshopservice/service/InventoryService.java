package com.eshop.eshopservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.eshop.eshopmodel.inventory.InventoryProduct;
import com.eshop.eshopmodel.inventory.InventoryProductDTO;
import com.eshop.eshoprepository.InventoryProductRepository;
import com.eshop.eshopservice.mapper.InventoryCustomModelMapper;
import com.eshop.exception.InventoryProductException;

/**
 * Service implementation class for Inventory Products management
 */

public class InventoryService implements InventoryServiceInterface {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private InventoryCustomModelMapper inventoryCustomModelMapper;
	
	@Autowired
	private InventoryProductRepository  inventoryProductRepository;

	/**
	 * Create a new product in the inventory for customer to purchase
	 * @param inventoryProductDTO object to create
	 * @return inventoryProductDTO object which was created in inventory
	 */
	@Override
	public InventoryProductDTO addInventoryProduct(InventoryProductDTO inventoryProductDTOObject) {
		InventoryProduct inventoryProductObject = inventoryCustomModelMapper.mapInventoryProductDTOToInventoryProduct(inventoryProductDTOObject);
		InventoryProduct inventoryProductReturnObject = inventoryProductRepository.save(inventoryProductObject);
		inventoryProductObject = null;
		return inventoryCustomModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProductReturnObject);
	}

	/**
	 * Get all InventoryProducts
	 * @return list of inventoryProductDTO objects
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProducts() {
		return inventoryProductRepository.findAll().stream().
				map(inventoryProduct -> inventoryCustomModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * Get all Inventory Products By name criteria
	 * @param inventoryProduct name
	 * @return matching list of inventory products
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProductsByName(String inventoryProductName) {
		return inventoryProductRepository.getAllInventoryProductsByName(inventoryProductName).stream().
				map(inventoryProduct -> inventoryCustomModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * Get all Inventory Products By price criteria
	 * @param lower price
	 * @param upper price
	 * @return matching list of inventory products
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProductsByPriceRange(long lowerBoundPrice, long upperBoundPrice) {
		return inventoryProductRepository.getAllInventoryProductsByPriceRange(lowerBoundPrice, upperBoundPrice).stream().
				map(inventoryProduct -> inventoryCustomModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * Update the inventory product
	 * @param inventoryProduct ID
	 * @param inventoryProductDTO object
	 * @return updated inventoryProductDTO object
	 * @throws InventoryProductException
	 */
	@Override
	public InventoryProductDTO updateInventoryProducts(int inventoryProductID, InventoryProductDTO inventoryProductDTOObject) 
			throws InventoryProductException {
		InventoryProduct inventoryProductRetrieveObject = inventoryProductRepository.findById(inventoryProductID).
				orElseThrow(() -> new InventoryProductException(messageSource.getMessage("InventoryProductNotFound", null, LocaleContextHolder.getLocale())));
		
		InventoryProduct inventoryProductObject = inventoryCustomModelMapper.mapInventoryProductDTOToInventoryProduct(inventoryProductDTOObject);
		
		if(inventoryProductID != inventoryProductObject.getInventoryProductID()) {
			throw new InventoryProductException(messageSource.getMessage("InventoryProductIDIncorrect", null, LocaleContextHolder.getLocale()));
		}

		//CAUTION : We do not change the ID of inventory Product
		inventoryProductRetrieveObject.setInventoryProductName(inventoryProductObject.getInventoryProductName());
		inventoryProductRetrieveObject.setInventoryProductPrice(inventoryProductObject.getInventoryProductPrice());
		inventoryProductRetrieveObject.setInventoryProductQuantity(inventoryProductObject.getInventoryProductQuantity());
		
		InventoryProduct inventoryProductReturnObject = inventoryProductRepository.save(inventoryProductRetrieveObject);
		inventoryProductObject = null;
		inventoryProductRetrieveObject = null;
		
		return inventoryCustomModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProductReturnObject);
	}

	/**
	 * Delete a specific inventory Product
	 * @param inventoryProduct ID
	 * @throws InventoryProductException
	 */
	@Override
	public void removeInventoryProduct(int inventoryProductID) throws InventoryProductException {
		if(!inventoryProductRepository.existsById(inventoryProductID)) {
			throw new InventoryProductException(messageSource.getMessage("InventoryProductNotFound", null, LocaleContextHolder.getLocale())); 
		}
		inventoryProductRepository.deleteById(inventoryProductID);
	}

}
