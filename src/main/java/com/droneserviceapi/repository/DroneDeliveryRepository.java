package com.droneserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.droneserviceapi.entity.MedicalDelivery;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneDeliveryRepository extends JpaRepository<MedicalDelivery, String> {

}
