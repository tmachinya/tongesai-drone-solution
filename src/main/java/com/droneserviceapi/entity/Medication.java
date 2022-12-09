package com.droneserviceapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medication")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication {
	@Id
	@Column(name = "code", columnDefinition = "VARCHAR(16) NOT NULL")
	private String code;

	@Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL")
	private String name;

	@Column(name = "weight", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weight;

	@Column(name = "medication_image")
	private String image;

	@Override
	public String toString() {
		return "Medication [code=" + code + ", name=" + name + ", weight=" + weight + ",  image=" + image + "]";
	}

}
