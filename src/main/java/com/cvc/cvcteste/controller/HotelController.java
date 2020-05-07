package com.cvc.cvcteste.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvc.cvcteste.service.IHotelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class HotelController {
	
	@Autowired
	IHotelService service;
	
	@ApiOperation(nickname = "hotelAvailV1", value = "Lista a cotação de uma viagem para todos os hotéis e quartos de uma cidade.", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/hotels/avail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPrice( @RequestParam(value = "cityCode", required = true) final String cityCode,
    										@RequestParam(value = "checkin", required = true) final String checkin,
    										@RequestParam(value = "checkout", required = true) final String checkout,
    										@RequestParam(value = "adults", required = true) final String adults,
    										@RequestParam(value = "child", required = true) final String child) {
		
		return service.getHotelsPrice(cityCode, checkin, checkout, adults, child);
		
    }
	
	@ApiOperation(nickname = "hotelIdV1", value = "Informa a cotação de uma viagem para o hotel informado.", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/hotels/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getHotelDetails(  @PathVariable("id") String id,
    												@RequestParam(value = "checkin", required = true) final String checkin,
    												@RequestParam(value = "checkout", required = true) final String checkout,
    												@RequestParam(value = "adults", required = true) final String adults,
    												@RequestParam(value = "child", required = true) final String child) {
		
		return service.getHotelDetails(id, checkin, checkout, adults, child);
    }
	
}
