package com.ennea.supplychainmanagement.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ennea.supplychainmanagement.entity.SupplyChainManagement;

public interface SupplyChainManagementRepo extends PagingAndSortingRepository<SupplyChainManagement, String> {

	List<SupplyChainManagement> findAllBySupplier(String supplierName, Pageable pageable);

	List<SupplyChainManagement> findAllBySupplierAndName(String supplierName, String productName, Pageable pageable);

}
