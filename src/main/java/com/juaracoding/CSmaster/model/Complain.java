package com.juaracoding.CSmaster.model;
/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author Farhan a.k.a Muhammad Farhan Kamil
Java Developer
Created On 15/03/2023 12:44
@Last Modified 15/03/2023 12:44
Version 1.0
*/

import jdk.jfr.Description;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "MstComplain")
public class Complain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDComplain")
	private Long idComplain;

	@Column(name = "NamaBarang",nullable = false,length = 25)
	private String namaBarang;


	@Column(name ="NoInvoice" , nullable = false)
	private String noInvoice = new String();

	@Column(name = "TypeBarang", nullable = false)
	private Integer typeBarang=1;

	@Column(name = "MerkBarang")
	private Date merkBarang;
	@Column(name = "Description")
	private Integer description;

	@Column(name = "Image", nullable = false)
	private String image ;

	public Long getIdComplain() {
		return idComplain;
	}

	public void setIdComplain(Long idComplain) {
		this.idComplain = idComplain;
	}

	public String getNamaBarang() {
		return namaBarang;
	}

	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}

	public String getNoInvoice() {
		return noInvoice;
	}

	public void setNoInvoice(String noInvoice) {
		this.noInvoice = noInvoice;
	}

	public Integer getTypeBarang() {
		return typeBarang;
	}

	public void setTypeBarang(Integer typeBarang) {
		this.typeBarang = typeBarang;
	}

	public Date getMerkBarang() {
		return merkBarang;
	}

	public void setMerkBarang(Date merkBarang) {
		this.merkBarang = merkBarang;
	}

	public Integer getDescription() {
		return description;
	}

	public void setDescription(Integer description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}

