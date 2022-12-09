package com.droneserviceapi;

import com.droneserviceapi.entity.Medication;
import com.droneserviceapi.repository.MedicationRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableWebSecurity
public class Application {
	private final MedicationRepository medicationRepository;
	public Application(MedicationRepository medicationRepository) {
		this.medicationRepository = medicationRepository;
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}
	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			Medication med1 = Medication.builder()
					.code("TM210291")
					.image("ton_image.png")
					.name("African Herbs")
					.weight(23)
					.build();
			Medication med2 = Medication.builder()
					.weight(34)
					.name("European")
					.code("MAC210291")
					.image("mac_image.png")
					.build();
			Medication med3 = Medication.builder()
					.weight(34)
					.name("Asian Herbs")
					.code("Champ210291")
					.image("champ_image.png")
					.build();
			Medication med4 = Medication.builder()
					.weight(34)
					.name("Australian")
					.code("Aus2102914")
					.image("aus_image.png")
					.build();

			List<Medication> meds = new ArrayList<>();
			Collections.addAll(meds, med1, med2, med3, med4);
			medicationRepository.saveAll(meds);
		};
	}

	
	
}