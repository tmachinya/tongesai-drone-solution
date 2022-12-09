package com.droneserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "drone_load")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadMedication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trackingid")
	private Integer trackingId;

	@Column(name = "source", columnDefinition = "VARCHAR(30) NOT NULL")
	private String source;

	@Column(name = "destination", columnDefinition = "VARCHAR(30) NOT NULL")
	private String destination;

	@Column(name = "createdon", columnDefinition = "VARCHAR(30) NOT NULL")
	private LocalDateTime createdon;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_serial_no", referencedColumnName = "serial_no")
	private Drone drone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_code", referencedColumnName = "code", unique = true)
	private Medication medication;

}
