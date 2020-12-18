package com.equinix.appops.dart.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.buisness.AppOpsDartCommonBusiness;
import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.model.UserPrefModel;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;

@RestController
@RequestMapping("/dart/common")
public class AppOpsDartPortalCommonController extends BaseContorller {
	
	Logger logger = LoggerFactory.getLogger(AppOpsDartPortalCommonController.class);
	
	@Autowired
	AppOpsDartCommonBusiness dartCommonBusiness;
	
	@RequestMapping(value = "/saveUserPreferences", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserPrefModel>> saveUserPreferences(@RequestBody UserPrefModel userPrefModel, HttpServletRequest req , 
			HttpServletResponse resp,HttpResponse httpResp) {
		try {
			String responseStatus = dartCommonBusiness.persistUserPreferences(userPrefModel);
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			logger.error("UUID : "+uuid+" Error : "+ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}
		return buildResponse(userPrefModel, "Available", "Not Available");
	}
	
	@RequestMapping(value = "/getUserPreferences", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<UserPrefModel>> getUserPreferences() {
		try {
			return buildResponse(dartCommonBusiness.getUserPreferences(), "Available", "Not Available");
		} catch (Exception ex) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,ex);
			logger.error("UUID : "+uuid+" Error : "+ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage(), uuid);
		}
	}
	
	@RequestMapping(value = "/checkCascadeFlag", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO<HashMap>> checkCascadeFlag(HttpServletRequest req , HttpServletResponse resp,HttpResponse httpResp) {
		try{	
		  String dfrId = req.getParameter("dfrId");
		  HashMap<String,String> resultMap = new HashMap<>();
		  String checkCascadeFlag = "N";
		  if(StringUtils.isNotEmpty(dfrId))
			  checkCascadeFlag = dartCommonBusiness.checkCascadeFlag(dfrId);
		  resultMap.put("cascadeflag", checkCascadeFlag);		  
		  return buildResponse(resultMap, "Available", "Not Available");
		}
		catch (Exception e) {
			String uuid = UUID.randomUUID().toString();
			logger.error(uuid,e);
			return buildErrorResponse(e.getMessage(), uuid);
		}finally {
			HttpClientUtils.closeQuietly(httpResp);
		}
	 }
}
