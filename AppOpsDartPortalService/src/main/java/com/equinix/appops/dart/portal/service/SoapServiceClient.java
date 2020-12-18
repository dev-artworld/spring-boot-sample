package com.equinix.appops.dart.portal.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.intecbilling.tresoap._2_0.afs_inbound.AFSInbound;
import com.intecbilling.tresoap._2_0.afs_inbound.AFSInboundPortType;
import com.siebel.eqxprocessdartbatches.EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS;
import com.siebel.eqxprocessdartbatches.EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS_Service;



@Configuration
public class SoapServiceClient {
	
	@Bean
	public EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS siebelBatchService() {
		try{
			EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS_Service service = new EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS_Service();
		return service.getEQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBSSOAP();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
		
	}
	
	@Bean
	public Gson getGson() throws Exception {
		return new Gson();
	}
	
	@Bean
	public AFSInboundPortType svSoapClient(){
		try{
		AFSInbound afsInBound = new AFSInbound();
		 return afsInBound.getAFSInboundHttpSoap11Endpoint();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
