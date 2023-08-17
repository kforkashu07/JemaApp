/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
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

@Table(name = "inventory_setting_status")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class InventorySettingStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	String name;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "refer_to_department")
	int referToDepartment;
	
	@Column(name = "department")
	Long department;

	@Column(name = "can_add_department")
	int canAddDocument;
	
	@Column(name = "can_add_comment")
	int canAddComment;	
	
	@Column(name = "status")
	int status;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;
}
