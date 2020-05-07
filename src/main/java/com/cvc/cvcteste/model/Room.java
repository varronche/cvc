package com.cvc.cvcteste.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Room implements Serializable{
	
	private static final long serialVersionUID = -8550169039891808707L;

	private Long roomID;
	
	private String categoryName;
	
	private BigDecimal totalPrice;
	
	@JsonAlias("price")
	private Price priceDetail;
	
}
