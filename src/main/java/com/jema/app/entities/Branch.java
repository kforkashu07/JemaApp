/* 

*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Mar-2023
*
*/

package com.jema.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "branch")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "image")
	String image;


	@Column(name = "name", unique = true)

	
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "location")
	String location;


	@Column(name = "email", unique = true)

	String email;

	@Column(name = "leader")
	Long leader;

	@Column(name = "contact")
	String contact;

	@Column(name = "department")
	Long department;

	@Column(name = "totalemployee")
	Long totalEmployee;
	
	@Column(name = "status")
	Integer status;
	
	@Column(name = "starttime")
    @Temporal(TemporalType.TIME)
    Date startTime;

	@Column(name = "endtime")
    @Temporal(TemporalType.TIME)
    Date endTime;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;

	
}
