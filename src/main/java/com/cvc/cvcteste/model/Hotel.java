package com.cvc.cvcteste.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Hotel implements Serializable{
	
	private static final long serialVersionUID = 8597809861874366264L;

	private Long id;
	
	private String cityName;
	
	private List<Room> rooms;
	
}
