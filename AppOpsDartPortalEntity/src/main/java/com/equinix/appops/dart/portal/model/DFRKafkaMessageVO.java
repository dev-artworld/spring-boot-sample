package com.equinix.appops.dart.portal.model;

import java.io.Serializable;

public class DFRKafkaMessageVO  implements Serializable{

	private static final long serialVersionUID = 1L;

	private String dfrId;
	
	private String dfrRequestId;
	
	public DFRKafkaMessageVO(String dfrId) {
		this.dfrId=dfrId;
	}
	
	public DFRKafkaMessageVO(String dfrId, String requestId) {
		this.dfrId=dfrId;
		this.dfrRequestId=requestId;
	}
	
	public String getDfrId() {
		return dfrId;
	}
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
	public String getDfrRequestId() {
		return dfrRequestId;
	}
	public void setDfrRequestId(String dfrRequestId) {
		this.dfrRequestId = dfrRequestId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DFRKafkaMessageVO [dfrId=");
		builder.append(dfrId);
		builder.append(", dfrRequestId=");
		builder.append(dfrRequestId);
		builder.append("]");
		return builder.toString();
	}
	
}
