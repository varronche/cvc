package com.cvc.cvcteste.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cvc.cvcteste.model.Hotel;
import com.cvc.cvcteste.model.Price;
import com.cvc.cvcteste.model.Room;
import com.cvc.cvcteste.service.partner.api.HotelApiService;

@Service
public class HotelService implements IHotelService {

	@Autowired
	HotelApiService apiService;

	@Override
	public ResponseEntity<Object> getHotelsPrice(String cityCode, String checkin, String checkout,
													String adults, String child) {

		try {

			Period period 	= Period.between(LocalDate.parse(checkin, DateTimeFormatter.ISO_OFFSET_DATE_TIME) , LocalDate.parse(checkout, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			int nbAdults 	= (adults != null) ? new Integer(adults) : 0;
			int nbChildren 	= (child != null)  ? new Integer(child)  : 0;

			List<Hotel> apiHotels = apiService.getHotelsByCity(cityCode);

			if (apiHotels == null)
				return new ResponseEntity<Object>("Erro ao consultar a api do parceiro", HttpStatus.BAD_REQUEST);

			List<Hotel> hotels = processCalc(period, apiHotels, nbAdults, nbChildren);
			
			return new ResponseEntity<Object>(hotels, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Error - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * Processamento paralelo do valor de todas as estadias em todos os hotéis retornados
	 */
	private List<Hotel> processCalc(Period period, List<Hotel> hotels, int adults, int children) {

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		CompletionService<Hotel> cs = new ExecutorCompletionService<>(executor);

		List<Future<Hotel>> futures = new ArrayList<>();

		for(Hotel hotel : hotels) 
			futures.add(cs.submit(() -> getTotal(period.getDays(), hotel, adults, children)));

		List<Hotel> newHotels = new ArrayList<Hotel>();

		while (futures.size() > 0) {
			Future<Hotel> f = cs.poll();
			if (f != null) {
				futures.remove(f);
				try {
					newHotels.add(f.get());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}

		return newHotels;
	}
	
	/*
	 * Retorna valor total da cotação
	 */
	private Hotel getTotal(Integer days, Hotel hotel, int adults, int children) {
		
		for(Room room : hotel.getRooms()) {
			
			Price price = room.getPriceDetail();
			
			//preço total para adultos e crianças
			BigDecimal adult = price.getPricePerDayAdult().multiply(new BigDecimal(days)).multiply(new BigDecimal(adults));
			BigDecimal child = price.getPricePerDayChild().multiply(new BigDecimal(days)).multiply(new BigDecimal(children));
			
			//preço da comissão para adultos  e crianças
			BigDecimal adultComission = adult.divide(new BigDecimal("0.7"), BigDecimal.ROUND_UP);
			BigDecimal childComission = (child.compareTo(BigDecimal.ZERO) == 0) ? child.divide(new BigDecimal("0.7"), BigDecimal.ROUND_UP) : new BigDecimal(0);
			
			BigDecimal total = adult.add(child).add(adultComission).add(childComission);	
			
			room.setTotalPrice(total);
		}
		return hotel;
	}

	@Override
	public ResponseEntity<Object> getHotelDetails(String id, String checkin, String checkout,
			String adults, String child) {
		
		try {

			Period period 	= Period.between(LocalDate.parse(checkin, DateTimeFormatter.ISO_OFFSET_DATE_TIME) , LocalDate.parse(checkout, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			int nbAdults 	= (adults != null) ? new Integer(adults) : 0;
			int nbChildren 	= (child != null)  ? new Integer(child)  : 0;

			Hotel apiHotel = apiService.getHotelsById(id);

			if (apiHotel == null)
				return new ResponseEntity<Object>("Erro ao consultar a api do parceiro", HttpStatus.BAD_REQUEST);

			Hotel hotel = getTotal(period.getDays(), apiHotel, nbAdults, nbChildren);
			
			return new ResponseEntity<Object>(hotel, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Error - " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
