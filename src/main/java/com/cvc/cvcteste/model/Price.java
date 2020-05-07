package com.cvc.cvcteste.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Price implements Serializable{
	
	private static final long serialVersionUID = -4953131728464494454L;

	@JsonAlias("adult")
	private BigDecimal pricePerDayAdult;
	
	@JsonAlias("child")
	private BigDecimal pricePerDayChild;
	
}
