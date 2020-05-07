package com.cvc.cvcteste.service;

import org.springframework.http.ResponseEntity;

/**
 * Serviços referente a cotação de hotéis
 * 
 * @author vanessa_arronche
 *
 */
public interface IHotelService {

	/**
	 * Retorna a cotação de uma viagem para todos os hotéis e quartos de uma cidade
	 * @param cityCode
	 * @param checkin
	 * @param checkout
	 * @param adults
	 * @param child
	 * @return
	 */
	ResponseEntity<Object> getHotelsPrice(String cityCode, String checkin, String checkout,
											String adults, String child);

	/**
	 * Retorna a cotação de uma viagem para o hotel informado
	 * @param id
	 * @param checkin
	 * @param checkout
	 * @param adults
	 * @param child
	 * @return
	 */
	ResponseEntity<Object> getHotelDetails(String id, String checkin, String checkout,
											String adults, String child);

}
