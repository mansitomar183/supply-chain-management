package com.ennea.supplychainmanagement.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ennea.supplychainmanagement.entity.SupplyChainManagement;
import com.ennea.supplychainmanagement.repository.SupplyChainManagementRepo;
import com.ennea.supplychainmanagement.util.CSVHelper;

@Service
public class SupplyChainManagementService {

	@Autowired
	SupplyChainManagementRepo repository;

	Long currentTime = System.currentTimeMillis();

	public void save(MultipartFile file) {
		try {
			List<SupplyChainManagement> tutorials = CSVHelper.csvToSupplyChainManagement(file.getInputStream());
			repository.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<SupplyChainManagement> getStockInfo(String supplierName, int pageNumber, int size) {
		Pageable pageable = PageRequest.of(pageNumber, size);
		return repository.findAllBySupplier(supplierName, pageable);

	}

	public List<SupplyChainManagement> getStockAndProductInfo(String supplierName, String productName, int pageNumber,
			int size) {
		Pageable pageable = PageRequest.of(pageNumber, size);
		return repository.findAllBySupplierAndName(supplierName, productName, pageable);
	}

	public List<SupplyChainManagement> getProductsInfo(String supplierName, int pageNumber, int size) {
		Pageable pageable = PageRequest.of(pageNumber, size);
		List<SupplyChainManagement> supplyChainManagement = new ArrayList<SupplyChainManagement>();
		supplyChainManagement = repository.findAllBySupplier(supplierName, pageable);

		List<SupplyChainManagement> newList = new ArrayList<SupplyChainManagement>();

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String str1 = formatter.format(date);
		LocalDate start_date = LocalDate.parse(str1, formatter2);

		for (SupplyChainManagement product : supplyChainManagement) {
			String str2 = product.getExp();
			LocalDate end_date = LocalDate.parse(str2, formatter2);
			Period diff = Period.between(start_date, end_date);
			if (diff.getYears() > 0 || diff.getMonths() > 0 || diff.getDays() > 0) {
				newList.add(product);
			}
		}

		return newList;

	}

}
