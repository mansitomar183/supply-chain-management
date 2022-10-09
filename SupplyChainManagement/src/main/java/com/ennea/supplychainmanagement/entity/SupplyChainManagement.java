package com.ennea.supplychainmanagement.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supplychainmanagement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplyChainManagement {

	@Id
	@GeneratedValue
	long id;
	String code;
	String name;
	String batch;
	long stock;
	long deal;
	long free;
	double mrp;
	double rate;
	String exp;
	String company;
	String supplier;

}
