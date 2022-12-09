package com.droneserviceapi.data.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneRegisterRequest {

	@NotBlank
	@NotNull
	private String serialNumber;

	@NotBlank
	@NotNull
	private String model;

	@NotNull
	private double weightLimit;

	@NotNull
	private BigDecimal battery;

	@NotBlank
	@NotNull
	private String state;

}
