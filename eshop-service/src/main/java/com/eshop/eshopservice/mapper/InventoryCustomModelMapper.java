package com.eshop.eshopservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.inventory.InventoryProduct;
import com.eshop.eshopmodel.inventory.InventoryProductDTO;

/**
 * Custom Model Mapper class to map InventoryPoduct to its DTO and vice versa.
 */

@Service
public class InventoryCustomModelMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * No argument constructor
	 */
	public InventoryCustomModelMapper() {
		
	}
	
	/**
	 * Map InventoryProductDTO Object to InventoryProduct
	 * @param InventoryProductDTO Object
	 * @return InventoryProduct Object
	 */
	public InventoryProduct mapInventoryProductDTOToInventoryProduct(InventoryProductDTO inventoryProductDTOObject) {
		return modelMapper.map(inventoryProductDTOObject, InventoryProduct.class);
	}
	
	/**
	 * Map InventoryProduct Object to InventoryProductDTO Object
	 * @param inventoryProduct Object
	 * @return InventoryProductDTO Object
	 */
	public InventoryProductDTO mapInventoryProductToInventoryProductDTO(InventoryProduct inventoryProductObject) {
		return modelMapper.map(inventoryProductObject, InventoryProductDTO.class);
	}

}
