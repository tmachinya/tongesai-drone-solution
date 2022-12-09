package com.droneserviceapi.data.payload.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.droneserviceapi.entity.Drone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableDroneResponse {

	private String status;
	private LocalDateTime timestamp;
	private List<Drone> drones;

}
