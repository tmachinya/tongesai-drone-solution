package com.droneserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.droneserviceapi.entity.Medication;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationPreloadingDataRepository extends JpaRepository<Medication, String> {

	
	
}
