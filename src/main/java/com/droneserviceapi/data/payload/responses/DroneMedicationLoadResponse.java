package com.droneserviceapi.data.payload.responses;

import java.time.LocalDateTime;

import com.droneserviceapi.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneMedicationLoadResponse {

	private String result;
	private String serialNumber;
	private LocalDateTime timestamp;
	Medication medication;

}
