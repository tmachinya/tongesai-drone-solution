package com.droneserviceapi.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.droneserviceapi.data.payload.requests.DroneRegisterRequest;
import com.droneserviceapi.data.payload.requests.LoadRequest;
import com.droneserviceapi.data.payload.responses.AvailableDroneResponse;
import com.droneserviceapi.data.payload.responses.DeliverDroneResponse;
import com.droneserviceapi.data.payload.responses.DroneBatteryDetailsResponse;
import com.droneserviceapi.data.payload.responses.DroneMedicationLoadResponse;
import com.droneserviceapi.data.payload.responses.LoadDroneResponse;
import com.droneserviceapi.data.payload.responses.RegisterDroneResponse;
import com.droneserviceapi.entity.Drone;
import com.droneserviceapi.entity.LoadMedication;
import com.droneserviceapi.entity.MedicalDelivery;
import com.droneserviceapi.entity.Medication;
import com.droneserviceapi.repository.DroneDeliveryRepository;
import com.droneserviceapi.repository.DroneRepository;
import com.droneserviceapi.repository.MedicationRepository;
import com.droneserviceapi.repository.loadDroneRepository;

@Service
@RequiredArgsConstructor
public class DroneServiceImplementation implements DroneService {
	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;
	private final loadDroneRepository loadDroneRepository;
	private final DroneDeliveryRepository droneDeliveryRepository;


	@Override
	public RegisterDroneResponse register(DroneRegisterRequest droneRequest) {
		Drone drone = Drone.builder()
				.serialNumber(droneRequest.getSerialNumber())
				.battery(droneRequest.getBattery())
				.model(droneRequest.getModel())
				.weightLimit(droneRequest.getWeightLimit())
				.state(droneRequest.getState())
				.build();
		droneRepository.save(drone);
		return RegisterDroneResponse.builder()
				.message("Drone created successfully")
				.result("Success")
				.serialNumber(drone.getSerialNumber())
				.timestamp(java.time.LocalDateTime.now())
				.build();
	}

	@Override
	public AvailableDroneResponse getAvailableDrones() {
		String state = "IDLE";
		List<Drone> drones = droneRepository.findAllByState(state);
		return new AvailableDroneResponse("success", java.time.LocalDateTime.now(), drones);
	}

	@Override
	public DroneBatteryDetailsResponse getBatteryLevel(String serialNum) {
		Drone drone = Drone
				.builder()
				.serialNumber(serialNum)
				.build();
		Drone droneBattery = droneRepository.findBySerialNumber(drone.getSerialNumber());
		if (droneBattery == null) {
			throw new RuntimeException("Drone battery level details not found");
		}

		DecimalFormat decFormat = new DecimalFormat("#%");
		return DroneBatteryDetailsResponse
				.builder()
				.status("success")
				.serialNumber(serialNum)
				.battery(decFormat.format(droneBattery.getBattery()))
				.timestamp(java.time.LocalDateTime.now())
				.build();
	}

	@Override
	public DroneMedicationLoadResponse getLoadedMedicationForADrone(String serialnumber) {

		LoadMedication loadMedication = loadDroneRepository.findByDrone(serialnumber);
		if(loadMedication==null) {
			throw new RuntimeException("No load Medication details for the specified drone");
		}
		return DroneMedicationLoadResponse
				.builder()
				.result("success")
				.serialNumber(loadMedication.getDrone().getSerialNumber())
				.timestamp(java.time.LocalDateTime.now())
				.medication(loadMedication.getMedication())
				.build();
	}

	@Override
	public LoadDroneResponse loadDrone(LoadRequest loadRequest, String serialNum, String code) {

		droneRepository.setUpdateState("LOADING", serialNum);
		Drone drone = droneRepository.findBySerialNumber(serialNum);
		Medication medication = medicationRepository.findByCode(code);
		LoadMedication checkLoad = loadDroneRepository.findByCode(code);
		
		if(checkLoad != null) {
			throw new RuntimeException("The Medication code already exist");

		}

//	necessary validations before loading
		if (drone == null) {
			throw new RuntimeException("Drone does not exist, please check the details");
		}

		if (medication == null) {
			throw new RuntimeException("The supplied medication cannot be found, please check ");
		}

		if (drone.getWeightLimit() < medication.getWeight()) {
			throw new RuntimeException("The Load is greater that the drone limit");
		}  

		if( drone.getBattery().compareTo(new BigDecimal("0.25")) < 0  ){
			throw new RuntimeException("OOPs, battery is below 25%, loading cannot happen");
		}

		LoadMedication loadMedication = LoadMedication.builder()
				.drone(drone)
				.medication(medication)
				.source(loadRequest.getSource())
				.destination(loadRequest.getDestination())
				.createdon(LocalDateTime.now())
				.build();
		loadDroneRepository.save(loadMedication);
		droneRepository.setUpdateState("LOADED", serialNum);


		return LoadDroneResponse
				.builder()
				.result("success")
				.message("Drone was Loaded successfully")
				.timestamp(LocalDateTime.now())
				.serialNumber(serialNum)
				.build();
	}

	@Override
	public DeliverDroneResponse deliverLoad(String serialNumber) {
		droneRepository.setUpdateState("DELIVERING", serialNumber);
		LoadMedication loadMedication = loadDroneRepository.findByDrone(serialNumber);
		
		if(loadMedication==null) {
			throw new RuntimeException("Drone specifies is not loaded or does not exist");
		}
		
		MedicalDelivery medicalDelivery = MedicalDelivery
				.builder()
				.loadMedication(loadMedication)
				.deliveryTime(java.time.LocalDateTime.now())
				.build();
		droneDeliveryRepository.save(medicalDelivery);
		droneRepository.setUpdateState("DELIVERED", serialNumber);

		return DeliverDroneResponse
				.builder()
				.result("success")
				.serialNumber(serialNumber)
				.message("Delivered successfully")
				.timestamp(LocalDateTime.now())
				.build();
	}
}
