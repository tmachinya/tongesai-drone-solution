package com.droneserviceapi.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.droneserviceapi.data.payload.requests.DroneRegisterRequest;
import com.droneserviceapi.data.payload.requests.LoadRequest;
import com.droneserviceapi.data.payload.responses.AvailableDroneResponse;
import com.droneserviceapi.data.payload.responses.DeliverDroneResponse;
import com.droneserviceapi.data.payload.responses.DroneBatteryDetailsResponse;
import com.droneserviceapi.data.payload.responses.DroneMedicationLoadResponse;
import com.droneserviceapi.data.payload.responses.LoadDroneResponse;
import com.droneserviceapi.data.payload.responses.RegisterDroneResponse;
import com.droneserviceapi.service.DroneServiceImplementation;

@RestController
@RequestMapping(path="/api")
@Validated
public class DroneMainController {
	private final DroneServiceImplementation droneService;

	public DroneMainController(DroneServiceImplementation droneService) {
		this.droneService = droneService;
	}

	@PostMapping(path="/drones", consumes = "application/json", produces = "application/json")
	public ResponseEntity<RegisterDroneResponse> registerDrone(
			@Valid @NotNull @RequestBody DroneRegisterRequest dronerequest) {
		RegisterDroneResponse newDrone = droneService.register(dronerequest);
		return new ResponseEntity<RegisterDroneResponse>(newDrone, HttpStatus.CREATED);
	}

	@GetMapping(path= "/drones", produces = "application/json")
	public ResponseEntity<AvailableDroneResponse> getAvailableDroneForLoading() {
		AvailableDroneResponse drones = droneService.getAvailableDrones();
		return new ResponseEntity<AvailableDroneResponse>(drones, HttpStatus.OK);
	}

	@GetMapping(path ="/drones/{serialNumber}",produces = "application/json")
	public ResponseEntity<DroneBatteryDetailsResponse> checkDroneBattery(@PathVariable String serialNumber){
		if (serialNumber == null || serialNumber.isEmpty()) {
			throw new RuntimeException("SerialNumber is Required");
		}
		DroneBatteryDetailsResponse droneBattery = droneService.getBatteryLevel(serialNumber);
		return new ResponseEntity<DroneBatteryDetailsResponse>(droneBattery, HttpStatus.CREATED);
	}

	@PostMapping(path = "/drones/{serialNum}/medications/{code}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<LoadDroneResponse> loadDroneWithMedication(@Valid @NotNull @RequestBody LoadRequest loadrequest, @PathVariable("serialNum") String serialNum, @PathVariable("code") String code) {
		LoadDroneResponse loadDrone = droneService.loadDrone(loadrequest, serialNum,code);
		return new ResponseEntity<LoadDroneResponse>(loadDrone, HttpStatus.OK);
	}

	@GetMapping(path = "drones/{serialNumber}/medications", produces = "application/json")
	public ResponseEntity<DroneMedicationLoadResponse> checkLoadedMedicationItem(@PathVariable("serialNumber") String serialNumber) {
		DroneMedicationLoadResponse droneLoads = droneService.getLoadedMedicationForADrone(serialNumber);
		return new ResponseEntity<DroneMedicationLoadResponse>(droneLoads, HttpStatus.OK);
	}

	@PostMapping(path = "/drones/{serialNumber}/medications",produces = "application/json")
	public ResponseEntity<DeliverDroneResponse> droneMedicalLoadDelivery(@NotNull @PathVariable("serialNumber") String serialNumber) {
		DeliverDroneResponse delivery = droneService.deliverLoad(serialNumber);
		return new ResponseEntity<DeliverDroneResponse>(delivery, HttpStatus.CREATED);
	}

}
