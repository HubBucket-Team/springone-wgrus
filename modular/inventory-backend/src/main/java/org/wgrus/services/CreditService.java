package org.wgrus.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CreditService {

	public String check(String customerId) {
		RestTemplate t = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<String>("");
		System.out.println("Doing HTTP request");
		ResponseEntity<String> r = t.exchange("http://cer-billing.chrisr.cloudfoundry.me/authorize/" + customerId,
				HttpMethod.GET, requestEntity, String.class);
		String body = r.getBody();
		System.out.println("Body=" + body);
    return body;
	}
}
