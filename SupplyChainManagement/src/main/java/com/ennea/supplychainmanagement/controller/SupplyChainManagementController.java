package com.ennea.supplychainmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ennea.supplychainmanagement.entity.SupplyChainManagement;
import com.ennea.supplychainmanagement.service.SupplyChainManagementService;
import com.ennea.supplychainmanagement.util.CSVHelper;
import com.ennea.supplychainmanagement.util.ResponseMessage;

@RestController
@RequestMapping("/SupplyChainManagement")
public class SupplyChainManagementController {

	@Autowired
	SupplyChainManagementService supplyChainManagementService;

	@PostMapping("/uploadFile")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (CSVHelper.hasCSVFormat(file)) {
			try {
				supplyChainManagementService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/{supplierName}/{pageNumber}/{size}")
	public List<SupplyChainManagement> getStockInfo(@PathVariable String supplierName, @PathVariable int pageNumber,
			@PathVariable int size) {
		return supplyChainManagementService.getStockInfo(supplierName, pageNumber, size);
	}

	@GetMapping("/{supplierName}/{productName}/{pageNumber}/{size}")
	public List<SupplyChainManagement> getStockAndProductInfo(@PathVariable String supplierName,
			@PathVariable String productName, @PathVariable int pageNumber, @PathVariable int size) {
		return supplyChainManagementService.getStockAndProductInfo(supplierName, productName, pageNumber, size);
	}

	@GetMapping("/productsNotYetExpired/{supplierName}/{pageNumber}/{size}")
	public List<SupplyChainManagement> getProductsNotYetExpired(@PathVariable String supplierName,
			@PathVariable int pageNumber, @PathVariable int size) {
		return supplyChainManagementService.getProductsInfo(supplierName, pageNumber, size);
	}
}
