package com.equinix.appops.dart.portal.model.errorsection;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"productName","rowNum","cellNum","messageStr"
})
public class ErrorMessage implements Serializable {
	
	@JsonProperty("rowNum")
	private long rowNum;
	
	@JsonProperty("cellNum")
	private long cellNum;
	
	@JsonProperty("messageStr")
	private String messageStr;
	
	@JsonProperty("rowNum")
	public long getRowNum() {
		return rowNum;
	}
	
	@JsonProperty("rowNum")
	public void setRowNum(long rowNum) {
		this.rowNum = rowNum;
	}
	
	@JsonProperty("cellNum")
	public long getCellNum() {
		return cellNum;
	}
	
	@JsonProperty("cellNum")
	public void setCellNum(long cellNum) {
		this.cellNum = cellNum;
	}
	
	@JsonProperty("messageStr")
	public String getMessageStr() {
		return messageStr;
	}
	
	@JsonProperty("messageStr")
	public void setMessageStr(String messageStr) {
		this.messageStr = messageStr;
	}
}
