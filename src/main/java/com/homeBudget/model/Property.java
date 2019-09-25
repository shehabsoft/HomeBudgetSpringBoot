package com.homeBudget.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the currency database table.
 * 
 */
@Entity
public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROPERTY_ID_GENERATOR", sequenceName="SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROPERTY_ID_GENERATOR")
	private int id;

	private String name;
	private String value;
 

	public Property() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	 

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

 
 
}