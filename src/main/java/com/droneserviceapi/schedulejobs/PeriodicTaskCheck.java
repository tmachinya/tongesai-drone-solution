package com.droneserviceapi.schedulejobs;

import java.text.DecimalFormat;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.droneserviceapi.entity.Drone;
import com.droneserviceapi.repository.DroneRepository;

@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class PeriodicTaskCheck {
	private final DroneRepository droneRepository;

    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        
        List<Drone> droneBatteryLevels = droneRepository.findAll();
        
    	DecimalFormat decFormat = new DecimalFormat("#%");
        for (Drone droneBatteryLevel : droneBatteryLevels) {

            log.info("Battery SerialNumber: " + droneBatteryLevel.getSerialNumber() + "  Battery Level " + decFormat.format(droneBatteryLevel.getBattery()));
        }
        Thread.sleep(5000);

    }
    
}
