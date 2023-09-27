package com.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopservice.service.LogisticsService;

/**
 * Controller for handling web requests regarding order and order products
 */

@RestController
public class LogisticsController {

	@Autowired
	private LogisticsService logisticsService;
	
}
