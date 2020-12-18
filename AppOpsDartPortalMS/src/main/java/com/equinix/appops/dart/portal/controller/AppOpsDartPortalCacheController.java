package com.equinix.appops.dart.portal.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.buisness.AppOpsCacheBusiness;
import com.equinix.appops.dart.portal.common.ResponseDTO;

@RestController
@RequestMapping(value={"/cache"})
public class AppOpsDartPortalCacheController extends BaseContorller {

	
	@Autowired
	AppOpsCacheBusiness appOpsCacheBusiness;
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartPortalCacheController.class);
	
	@RequestMapping(value = "/refreshAttributeConfig", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> getAttributeView(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try {
			String result = appOpsCacheBusiness.refreshAttributeConfigCache();
		 return buildResponse(result, "Available", "Api discountinued");
		}catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
	
	@RequestMapping(value = "/refreshOtherAttributes", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<String>> refreshOtherAttributes(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try {
			String result = appOpsCacheBusiness.refreshOtherAttributesCache();
		 return buildResponse(result, "Available", "Api discountinued");
		}catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
}
