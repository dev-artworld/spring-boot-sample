package com.equinix.appops.dart.portal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.common.ResponseDTO;
import com.equinix.appops.dart.portal.common.Status;



@RestController
public class BaseContorller<V> {

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(com.equinix.appops.dart.portal.common.ServiceConstant.DATE_PATTERN);
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public  ResponseEntity<ResponseDTO<V>> buildCollectionResponse(V data, String available,
			String notAvailable)  {
		ResponseDTO<V> responseDTO;
		if (null == data) {
			responseDTO = new ResponseDTO<>(notAvailable, Status.FAIL, data);
		} else {
			responseDTO = new ResponseDTO<>(available, data);
		}
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseDTO<V>> buildResponse(V data, String available,
			String notAvailable)  {
		ResponseDTO<V> responseDTO;
		if (null == data) {
			responseDTO = new ResponseDTO<>(notAvailable, Status.FAIL, data);
		} else {
			responseDTO = new ResponseDTO<>(available, data);
		}
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	/**
	 * This method is generic for all API's return response dto.
	 * @param bindingResult
	 * @param typeClass
	 * @return
	 */
	public ResponseDTO getResponseDTO(BindingResult bindingResult) {
		ResponseDTO responseDTO = new ResponseDTO();
		if (bindingResult.hasErrors()) {
			StringBuilder errorString = new StringBuilder();
			for (ObjectError error : bindingResult.getAllErrors()) {
				errorString.append(error.getDefaultMessage() + "</br>");
			}
			responseDTO.setMsg(errorString.toString());
			responseDTO.setStatus(Status.FAIL);
			return responseDTO;

		}
		return responseDTO;
	}
	
	public ResponseEntity<ResponseDTO<V>> buildErrorResponse(String message, String uuid)  {
		message = "uuid="+uuid+", Error:"+message;
		ResponseDTO<V> responseDTO = new ResponseDTO<>(message, Status.FAIL, null);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
