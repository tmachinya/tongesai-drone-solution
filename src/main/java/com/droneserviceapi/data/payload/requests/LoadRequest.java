package com.droneserviceapi.data.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadRequest {

//	@NotNull
//	@NotBlank
//	private String serialNumber;

	@NotNull
	@NotBlank
	private String source;
	
	@NotNull
	@NotBlank
	private String destination;

//	@NotNull
//	@NotBlank
//	private String code;

}
