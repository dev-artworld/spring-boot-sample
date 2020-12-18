package com.caplogix.template;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.equinix.appops.dart.portal.model.adapter.DartRequest;

/**
 * 
 * @author MM
 *
 */
@Service
public class HTTPConsumerServiceImpl implements HTTPConsumerService {

	/**
	 * 
	 */
	@Autowired
	RestTemplate consumerTemplate;
	
	static final String DART_REQ_URL = "http://qacaplogix.corp.equinix.com/CapLogixAPI/restservice/caplogix/dart/processDartRequest";
	/**
	 * 
	 * @return
	 */
	
	@Override
	public String processDartRequest(DartRequest dartRequest) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<DartRequest> entityDartRequest = new HttpEntity<DartRequest>(dartRequest, headers);
		return consumerTemplate.exchange(
				DART_REQ_URL, HttpMethod.POST, entityDartRequest, String.class).getBody();
	}
}
