package com.cvc.cvcteste.service.partner.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cvc.cvcteste.model.Hotel;

@Service
public class HotelApiService {

	Logger log = LoggerFactory.getLogger(HotelApiService.class);

	@Value("${client.service.hotels}")
	private String url;

	/**
	 * API Parceiro - Retorna uma lista de hotéis por cidade
	 * @param cityCode
	 * @return
	 */
	public List<Hotel> getHotelsByCity(String cityCode) {

		log.info("*** HotelsByCity ***");

		try {

			ResponseEntity<List<Hotel>> hotels =
					new RestTemplate().exchange(url + "avail/" + cityCode,
							HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
					});

			if (hotels != null && !hotels.getBody().isEmpty())
				return hotels.getBody();

		} catch (Exception e) {
			log.error("Error - getHotelsByCity " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * API Parceiro - Retorna detalhes de um hotel pelo id informado.
	 * @param id
	 * @return
	 */
	public Hotel getHotelsById(String id) {

		log.info("*** HotelsById ***");

		try {

			ResponseEntity<List<Hotel>> hotels =
					new RestTemplate().exchange(url + id,
							HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
					});

			if (hotels != null && !hotels.getBody().isEmpty())
				return hotels.getBody().get(0);

		} catch (Exception e) {
			log.error("Error - getHotelsById " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return null;
	}
}
