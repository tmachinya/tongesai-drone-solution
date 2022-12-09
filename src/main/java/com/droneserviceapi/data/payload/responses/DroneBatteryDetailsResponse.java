package com.droneserviceapi.data.payload.responses;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class DroneBatteryDetailsResponse {

	private String status;
	private String serialNumber;
	private String battery;
	private LocalDateTime timestamp;

}
