package com.juaracoding.CSmaster.dto;
/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author Farhan a.k.a Muhammad Farhan Kamil
Java Developer
Created On 15/03/2023 11:32
@Last Modified 15/03/2023 11:32
Version 1.0
*/

import com.juaracoding.CSmaster.utils.ConstantMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ComplainDTO {
	private Long idComplain;
	@NotNull
	@NotEmpty
	@Length(message = ConstantMessage.WARNING_MENU_NAME_LENGTH, max = 25)
	private String namaComplain;

	public Long getIdComplain() {
		return idComplain;
	}

	public void setIdComplain(Long idComplain) {
		this.idComplain = idComplain;
	}

	public String getNamaComplain() {
		return namaComplain;
	}

	public void setNamaComplain(String namaComplain) {
		this.namaComplain = namaComplain;
	}
}
