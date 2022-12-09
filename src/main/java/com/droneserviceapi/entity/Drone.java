package com.droneserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_drone")
@Entity
@Builder
public class Drone {

	@Id
	@Column(name = "serial_no", columnDefinition = "VARCHAR(16) NOT NULL")
	private String serialNumber;

	@Column(name = "model", columnDefinition = "VARCHAR(50) NOT NULL") // Lightweight, Middleweight, Cruiserweight,
	private String model;
	
	@Column(name = "weight_limit", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weightLimit;

	@Column(name = "battery",precision = 3, scale = 2)
	private BigDecimal battery;

	@Column(name = "drone_state", columnDefinition = "VARCHAR(20) NOT NULL") // IDLE, LOADING, LOADED, DELIVERING,
	private String state;
}
