package com.droneserviceapi.service;

import org.springframework.stereotype.Component;
import com.droneserviceapi.data.payload.requests.DroneRegisterRequest;
import com.droneserviceapi.data.payload.requests.LoadRequest;
import com.droneserviceapi.data.payload.responses.AvailableDroneResponse;
import com.droneserviceapi.data.payload.responses.DeliverDroneResponse;
import com.droneserviceapi.data.payload.responses.DroneBatteryDetailsResponse;
import com.droneserviceapi.data.payload.responses.DroneMedicationLoadResponse;
import com.droneserviceapi.data.payload.responses.LoadDroneResponse;
import com.droneserviceapi.data.payload.responses.RegisterDroneResponse;

@Component
public interface DroneService {
	
	RegisterDroneResponse register(DroneRegisterRequest drone);

	DroneBatteryDetailsResponse getBatteryLevel(String serialNum) throws Exception;
	
	DroneMedicationLoadResponse getLoadedMedicationForADrone(String serialno);
	
	AvailableDroneResponse getAvailableDrones();
	
	LoadDroneResponse loadDrone(LoadRequest loadRequest,String serialNum, String code);
	
	DeliverDroneResponse deliverLoad(String serialNumber);
	
}
