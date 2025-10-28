package com.codegnan.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/* Doctor extends Person and doctor specific fields.
 * *A doctor can have multiple visits.(one-to-many relatonship)
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)   //include person fields in equal/hashcode
public class Doctor extends Person{ 
	@NotBlank(message="specialization is mandatory")
	private String specialization;
	@Min(value=0,message="Experiance cannot be negative")
	private Integer experience;
	@NotBlank(message="Degree are Mandatory")
	private String degrees;
	@Min(value=0,message="salary cannot be negative")
	private Double salary;
	@OneToMany(mappedBy="doctor", cascade=CascadeType.ALL)
	@JsonIgnore //prevent infinite recursion when converting to json
	private List<Visit> visits;

}
