package com.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.inventory.InventoryProductDTO;
import com.eshop.eshopservice.service.InventoryService;
import com.eshop.exception.InventoryProductException;

import jakarta.validation.Valid;

@RestController
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	/**
	 * ADMIN PRIVILEDGE : Create a new product in the inventory for customer to purchase
	 * @param inventoryProductDTO object to create
	 * @return inventoryProductDTO object which was created in inventory
	 */
	@PostMapping("/inventory/add")
	public ResponseEntity<InventoryProductDTO> addInventoryProduct(@RequestBody(required=true) @Valid InventoryProductDTO inventoryProductDTOObject) {
		return new ResponseEntity<InventoryProductDTO> (inventoryService.addInventoryProduct(inventoryProductDTOObject),HttpStatus.CREATED);
	}

	/**
	 * ADMIN PRIVILEDGE : Get all Inventory Products
	 * @return list of inventoryProductDTO objects
	 */
	@GetMapping(value = "/inventory/search")
	public ResponseEntity<List<InventoryProductDTO>> getAllInventoryProducts()  {
		return new ResponseEntity<List<InventoryProductDTO>> (inventoryService.getAllInventoryProducts(), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get all Inventory Products By name criteria
	 * @param inventoryProduct name
	 * @return matching list of inventory products
	 */
	@GetMapping(value = "/inventory/search", params = {"inventoryProductName"})
	public ResponseEntity<List<InventoryProductDTO>> getAllInventoryProductsByName(
			@RequestParam(name="inventoryProductName", required=true) String inventoryProductName){
		return new ResponseEntity<List<InventoryProductDTO>> (inventoryService.getAllInventoryProductsByName(inventoryProductName), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get all Inventory Products By price criteria
	 * @param lower price
	 * @param upper price
	 * @return matching list of inventory products
	 */
	@GetMapping(value = "/inventory/search", params = {"lowerBound, upperBound"})
	public ResponseEntity<List<InventoryProductDTO>> getAllInventoryProductsByPriceRange(
			@RequestParam(name="lowerBound", required=true) long lowerBoundPrice, @RequestParam(name="upperBound", required=true) long upperBoundPrice) {
		return new ResponseEntity<List<InventoryProductDTO>> (inventoryService.getAllInventoryProductsByPriceRange(lowerBoundPrice, 
				upperBoundPrice), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Update the inventory product
	 * @param inventoryProduct ID
	 * @param inventoryProductDTO object
	 * @return updated inventoryProductDTO object
	 * @throws InventoryProductException
	 */
	@PutMapping("/inventory/update/{inventoryProductID}")	
	public ResponseEntity<InventoryProductDTO> updateInventoryProduct(
			@PathVariable(name="inventoryProductID", required=true) long inventoryProductID, @RequestBody @Valid InventoryProductDTO inventoryProductDTOObject) 
			throws InventoryProductException {
		return new ResponseEntity<InventoryProductDTO>(inventoryService.updateInventoryProduct(inventoryProductID, inventoryProductDTOObject),
				HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Delete a specific inventory Product
	 * @param inventoryProduct ID
	 * @throws InventoryProductException
	 */
	@DeleteMapping("/inventory/delete/{inventoryProductID}")	
	public ResponseEntity<Object> removeInventoryProduct(@PathVariable(name="inventoryProductID", required=true) long inventoryProductID) throws InventoryProductException  {
		inventoryService.removeInventoryProduct(inventoryProductID);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
