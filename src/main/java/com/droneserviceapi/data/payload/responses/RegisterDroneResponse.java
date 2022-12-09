package com.droneserviceapi.data.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDroneResponse {

	private String result;
	private String serialNumber;
	private String message;
	private LocalDateTime timestamp;

}
