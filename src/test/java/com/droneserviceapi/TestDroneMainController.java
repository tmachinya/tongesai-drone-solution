package com.droneserviceapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.droneserviceapi.controller.DroneMainController;
import com.droneserviceapi.data.payload.requests.DroneGetBatteryRequest;
import com.droneserviceapi.data.payload.requests.DroneRegisterRequest;
import com.droneserviceapi.data.payload.requests.LoadRequest;
import com.droneserviceapi.data.payload.responses.AvailableDroneResponse;
import com.droneserviceapi.data.payload.responses.DroneBatteryDetailsResponse;
import com.droneserviceapi.data.payload.responses.DroneMedicationLoadResponse;
import com.droneserviceapi.data.payload.responses.LoadDroneResponse;
import com.droneserviceapi.data.payload.responses.RegisterDroneResponse;
import com.droneserviceapi.entity.Drone;
import com.droneserviceapi.entity.Medication;
import com.droneserviceapi.service.DroneServiceImplementation;

@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
class TestDroneMainController {

	@InjectMocks
	DroneMainController droneMainController;
	@Mock
    DroneServiceImplementation droneService;

	@Test
	public void testRegisterDrone() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		RegisterDroneResponse registerDroneResponse =  RegisterDroneResponse
				.builder()
				.message("Drone created successfully")
				.result("Success")
				.serialNumber("AEH85152022")
				.timestamp(java.time.LocalDateTime.now())
				.build();

		when(droneService.register(any(DroneRegisterRequest.class))).thenReturn(registerDroneResponse);

		DroneRegisterRequest drone = DroneRegisterRequest
				.builder()
				.serialNumber("AEH85152022")
				.model("Chibaba")
				.weightLimit(2300)
				.battery(new BigDecimal("0.95"))
				.state("Delivered")
				.build();

		ResponseEntity<RegisterDroneResponse> responseEntity = droneMainController.registerDrone(drone);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody()).isEqualTo(registerDroneResponse);
		//assertEquals(registerDroneResponse, responseEntity);

	}

	@Test
	void testGetAvailableDroneForLoading() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

//		List<Drone> list = new ArrayList<Drone>();
		Drone drone1 = Drone
				.builder()
				.state("IDLE")
				.serialNumber("AEH85152022")
				.weightLimit(1020)
				.model("Sonny")
				.battery(new BigDecimal("0.95"))
				.build();

		Drone drone2 = Drone
				.builder()
				.state("IDLE")
				.serialNumber("AEH84132303")
				.weightLimit(1030)
				.model("SAMSUNG")
				.battery(new BigDecimal("0.60"))
				.build();

		List<Drone> list = new ArrayList<>();
		Collections.addAll(list,drone1, drone2);

		AvailableDroneResponse availableDrone = new AvailableDroneResponse("success", java.time.LocalDateTime.now(),list);

		when(droneService.getAvailableDrones()).thenReturn(availableDrone);

		ResponseEntity<AvailableDroneResponse> responseEntity = droneMainController.getAvailableDroneForLoading();
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200); // Created
		assertThat(Objects.requireNonNull(responseEntity.getBody()).getStatus()).isEqualTo("success");
		assertThat(responseEntity.getBody().getDrones().size()).isEqualTo(2);

	}

	@Test
	public void testCheckDroneBattery() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		DroneBatteryDetailsResponse droneBattery = DroneBatteryDetailsResponse
				.builder()
				.battery("86%")
				.status("Success")
				.serialNumber("AFM4086202234")
				.timestamp(java.time.LocalDateTime.now())
				.build();
		when(droneService.getBatteryLevel(anyString())).thenReturn(droneBattery);

		ResponseEntity<DroneBatteryDetailsResponse> responseEntity = droneMainController
				.checkDroneBattery("AFM4086202234");
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody()).isEqualTo(droneBattery);

	}

	@Test
	public void testLoadDroneWithMedication() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		LoadDroneResponse droneResponse = LoadDroneResponse
				.builder()
				.result("Success")
				.serialNumber("AEH85152022")
				.message("Drone Loaded successfully")
				.timestamp(java.time.LocalDateTime.now())
				.build();

		when(droneService.loadDrone(any(LoadRequest.class), anyString(), anyString())).thenReturn(droneResponse);

		LoadRequest loadRequest =  LoadRequest
				.builder()
				.destination("Chitungwiza")
				.source("Marlborough")
				.build();
		ResponseEntity<LoadDroneResponse> responseEntity = droneMainController.loadDroneWithMedication(loadRequest, "AEH85152022","TONGCHAMP1991");
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(droneResponse);

	}

	@Test
	public void testCheckLoadedMedicationItem() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Medication medication =  Medication
				.builder()
				.image("tongchamp_img.png")
				.code("Th7889545678")
				.name("Panadol")
				.weight(65)
				.build();

		DroneMedicationLoadResponse droneMedicationLoadRsponse = DroneMedicationLoadResponse
				.builder()
				.result("Success")
				.serialNumber("AEH85152022")
				.timestamp(java.time.LocalDateTime.now())
				.medication(medication)
				.build();

		String serialNumber = "Q23RT5676697";
		when(droneService.getLoadedMedicationForADrone(serialNumber)).thenReturn(droneMedicationLoadRsponse);

		ResponseEntity<DroneMedicationLoadResponse> responseEntity = droneMainController
				.checkLoadedMedicationItem(serialNumber);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(droneMedicationLoadRsponse);

	}

}
