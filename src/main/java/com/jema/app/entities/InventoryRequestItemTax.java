/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-May-2023
*
*/

package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "inventory_request_item_tax")
@Entity
@Data
@Getter
@Setter
public class InventoryRequestItemTax {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tax")
	private Long tax;
}
