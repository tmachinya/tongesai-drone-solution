package com.droneserviceapi.data.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneGetBatteryRequest {

	@NotBlank
	@NotNull
	private String serialNumber;

}
