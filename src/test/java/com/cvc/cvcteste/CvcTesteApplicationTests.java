package com.cvc.cvcteste;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CvcTesteApplicationTests {
	
	@LocalServerPort
    int randomServerPort;

	@Test
	public void testGetPrice() {
	    final String baseUrl = "http://localhost:"+randomServerPort+"/api/v1/hotels/16?checkin=2020-05-20T00:00:00.000Z&checkout=2020-05-25T00:00:00.000Z&adults=1&child=0";
	 
	    ResponseEntity<Object> result = new RestTemplate().exchange(baseUrl,
				HttpMethod.GET, null, Object.class);
	     
	    Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testGetHotelDetails() {
	    final String baseUrl = "http://localhost:"+randomServerPort+"/api/v1/hotels/avail?cityCode=1032&checkin=2020-05-20T00:00:00.000Z&checkout=2020-05-25T00:00:00.000Z&adults=2&child=2";
	 
	    ResponseEntity<Object> result = new RestTemplate().exchange(baseUrl,
				HttpMethod.GET, null, Object.class);
	     
	    Assert.assertEquals(200, result.getStatusCodeValue());
	}
}
