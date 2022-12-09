package com.droneserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.droneserviceapi.entity.Medication;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
	@Query(value = "SELECT * from medication t where t.code =:id ", nativeQuery = true) // using @query with
	Medication findByCode(@Param("id") String id);

}
