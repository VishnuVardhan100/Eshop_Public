package com.eshop.eshopinventoryservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshopinventoryservice.exception.InventoryProductException;
import com.eshop.eshopinventoryservice.model.inventory.InventoryProduct;
import com.eshop.eshopinventoryservice.model.inventory.InventoryProductDTO;
import com.eshop.eshopinventoryservice.model.logistics.OrderProduct;
import com.eshop.eshopinventoryservice.repository.InventoryProductRepository;
import com.eshop.eshopinventoryservice.service.helper.InventoryExcelHelper;
import com.eshop.eshopinventoryservice.service.helper.InventoryModelMapper;
import com.eshop.eshopinventoryservice.service.helper.InventoryProductAccountant;

/**
 * Service implementation class for Inventory Products management
 */

@Service
public class InventoryService implements InventoryServiceInterface {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private InventoryModelMapper inventoryModelMapper;
	
	@Autowired
	private InventoryProductAccountant inventoryProductAccountant;
	
	@Autowired
	private InventoryExcelHelper inventoryExcelHelper;
	
	@Autowired
	private InventoryProductRepository  inventoryProductRepository;
	
	/**
	 * ADMIN PRIVILEGE : Create a new product in the inventory for customer to purchase
	 * @param inventoryProductDTO object to create
	 * @return inventoryProductDTO object which was created in inventory
	 */
	@Override
	public InventoryProductDTO addInventoryProduct(InventoryProductDTO inventoryProductDTOObject) {
		InventoryProduct inventoryProductObject = inventoryModelMapper.mapInventoryProductDTOToInventoryProduct(inventoryProductDTOObject);
		inventoryProductObject = inventoryProductAccountant.adjustNewAdditionQuantity(inventoryProductObject);
		InventoryProduct inventoryProductReturnObject = inventoryProductRepository.save(inventoryProductObject);
		return inventoryModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProductReturnObject);
	}

	/**
	 * ADMIN PRIVILEGE : Create a list of new products in the inventory for customer to purchase
	 * @param file containing list of inventoryProductDTO objects to create
	 * @return inventoryProductDTO objects which were created in inventory
	 */
	public List<InventoryProductDTO> addMultipleInventoryProducts(MultipartFile file) throws IOException {
		List<InventoryProductDTO> inventoryProductDTOs = inventoryExcelHelper.excelToInventoryProductDTOs(file.getInputStream());
		List<InventoryProductDTO> savedInventoryProductDTOs = new ArrayList<>();

		for(InventoryProductDTO inventoryProductDTOObject : inventoryProductDTOs) {
			InventoryProduct inventoryProductObject = inventoryModelMapper.mapInventoryProductDTOToInventoryProduct(inventoryProductDTOObject);
			inventoryProductObject = inventoryProductAccountant.adjustNewAdditionQuantity(inventoryProductObject);
			InventoryProduct savedInventoryProductObject = inventoryProductRepository.save(inventoryProductObject);
			savedInventoryProductDTOs.add(inventoryModelMapper.mapInventoryProductToInventoryProductDTO(savedInventoryProductObject));
		}

		return savedInventoryProductDTOs;
	}

	/**
	 * ADMIN PRIVILEGE : Get all InventoryProducts
	 * @return list of inventoryProductDTO objects
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProducts() {
		return inventoryProductRepository.findAll().stream().
				map(inventoryProduct -> inventoryModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * ADMIN PRIVILEGE : Get all Inventory Products By name criteria
	 * @param inventoryProduct name
	 * @return matching list of inventory products
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProductsByName(String inventoryProductName) {
		return inventoryProductRepository.getAllInventoryProductsByName(inventoryProductName).stream().
				map(inventoryProduct -> inventoryModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * ADMIN PRIVILEGE : Get all Inventory Products By price criteria
	 * @param lower price
	 * @param upper price
	 * @return matching list of inventory products
	 */
	@Override
	public List<InventoryProductDTO> getAllInventoryProductsByPriceRange(long lowerBoundPrice, long upperBoundPrice) {
		return inventoryProductRepository.getAllInventoryProductsByPriceRange(lowerBoundPrice, upperBoundPrice).stream().
				map(inventoryProduct -> inventoryModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProduct)).
				collect(Collectors.toList());
	}

	/**
	 * ADMIN PRIVILEGE : Update the inventory product
	 * @param inventoryProduct ID
	 * @param inventoryProductDTO object
	 * @return updated inventoryProductDTO object
	 * @throws InventoryProductException inventoryProductException
	 */
	@Override
	public InventoryProductDTO updateInventoryProduct(long inventoryProductID, InventoryProductDTO inventoryProductDTOObject) 
			throws InventoryProductException {
		InventoryProduct inventoryProductRetrieveObject = inventoryProductRepository.findById(inventoryProductID).
				orElseThrow(() -> new InventoryProductException(messageSource.getMessage("InventoryProductNotFound", null, LocaleContextHolder.getLocale())));
		
		InventoryProduct inventoryProductObject = inventoryModelMapper.mapInventoryProductDTOToInventoryProduct(inventoryProductDTOObject);
		
		if(inventoryProductID != inventoryProductObject.getInventoryProductID()) {
			throw new InventoryProductException(messageSource.getMessage("InventoryProductIDIncorrect", null, LocaleContextHolder.getLocale()));
		}

		//CAUTION : We do not change the ID of inventory Product
		inventoryProductRetrieveObject.setInventoryProductName(inventoryProductObject.getInventoryProductName());
		inventoryProductRetrieveObject.setInventoryProductPrice(inventoryProductObject.getInventoryProductPrice());
		inventoryProductRetrieveObject.setInventoryProductQuantity(inventoryProductObject.getInventoryProductQuantity());
		
		InventoryProduct inventoryProductReturnObject = inventoryProductRepository.save(inventoryProductRetrieveObject);

		return inventoryModelMapper.mapInventoryProductToInventoryProductDTO(inventoryProductReturnObject);
	}

	/**
	 * ADMIN PRIVILEGE : Delete a specific inventory Product
	 * @param inventoryProduct ID
	 * @throws InventoryProductException inventoryProductException
	 */
	@Override
	public void removeInventoryProduct(long inventoryProductID) throws InventoryProductException {
		if(!inventoryProductRepository.existsById(inventoryProductID)) {
			throw new InventoryProductException(messageSource.getMessage("InventoryProductNotFound", null, LocaleContextHolder.getLocale())); 
		}
		inventoryProductRepository.deleteById(inventoryProductID);
	}
	
	/**
	 * Perform necessary checking if product is available in inventory and adjust quantity
	 * based on user order requirements
	 * @param InventoryProductIDList - list of inventory product IDs
	 * @param OrderProductList - list of order products
	 * @throws Inventory product exception
	 */
	@Override
	public void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList)
			throws InventoryProductException {
		inventoryProductAccountant.performInventoryQuantityCheckAndAdjust( inventoryProductIDList, orderProductList);
	}

}
