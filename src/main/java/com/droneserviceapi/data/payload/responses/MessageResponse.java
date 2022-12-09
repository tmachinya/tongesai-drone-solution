package com.droneserviceapi.data.payload.responses;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MessageResponse {
	private String result;
	private String message;
	private LocalDateTime timestamp;

}
