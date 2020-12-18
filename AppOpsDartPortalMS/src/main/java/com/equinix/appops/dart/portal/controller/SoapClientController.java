package com.equinix.appops.dart.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restservice/v1.0/")
public class SoapClientController {/*
	
	@Autowired
	EQXSpcEAISpcDARTSpcBatchSpcProcessingSpcBS siebelBatchService;

	@Autowired
	Gson gson;
	
	@GetMapping("soap/caplogix")
	public @ResponseBody String retrieve() {		
		UpsertSpcDARTSpcBatchInput upsertSpcDARTSpcBatchInput = new UpsertSpcDARTSpcBatchInput();
		ListOfEqxDartBatchIo listOfEqxDartBatchIo = new ListOfEqxDartBatchIo();
		EqxDartBatch eqxDartBatch = new EqxDartBatch();
		//eqxDartBatch.setEQXBatchStatus("Not Started");
		eqxDartBatch.setEQXDFRID("5555506");
		eqxDartBatch.setEQXLoginName("PNAGAGARAJAN");
		ListOfEqxDartBatchLines listOfEqxDartBatchLines = new ListOfEqxDartBatchLines();
		
		
		EqxDartBatchLines eqxDartBatchLines = new EqxDartBatchLines();
		//eqxDartBatchLines.setEQXCabinetNumber("0301");
		//eqxDartBatchLines.setEQXCageNumber("NY4:CAG001:00");
		eqxDartBatchLines.setEQXCustUCMId("WRQWER2343546EWTREWTWE");
		eqxDartBatchLines.setEQXDFRItemId("45557.1");
		//eqxDartBatchLines.setEQXLineStatus("Not Started");	
		eqxDartBatchLines.setEQXOperation("Create POE");
		eqxDartBatchLines.setEQXPOEName("Network Cable Connection");
		eqxDartBatchLines.setEQXPOFAssetNum("4-2323284926");
		eqxDartBatchLines.setEQXPOFName("Cross Connect");
		
		
		ListOfEqxDartBatchLineXa listOfEqxDartBatchLineXa = new ListOfEqxDartBatchLineXa();
		EqxDartBatchLineXa eqxDartBatchLineXa = new EqxDartBatchLineXa();
		eqxDartBatchLineXa.setEQXAttributeName("Media Type1");
		eqxDartBatchLineXa.setEQXAttributeType("Chartacter");
		eqxDartBatchLineXa.setEQXAttributeValue("Single Mode Fiber");
		listOfEqxDartBatchLineXa.getEqxDartBatchLineXa().add(eqxDartBatchLineXa );
		
		eqxDartBatchLineXa = new EqxDartBatchLineXa();
		eqxDartBatchLineXa.setEQXAttributeName("Media Type2");
		eqxDartBatchLineXa.setEQXAttributeType("Chartacter");
		eqxDartBatchLineXa.setEQXAttributeValue("Single Mode Fiber1");
		
		listOfEqxDartBatchLineXa.getEqxDartBatchLineXa().add(eqxDartBatchLineXa );
		
		
		eqxDartBatchLines.setListOfEqxDartBatchLineXa(listOfEqxDartBatchLineXa);
		listOfEqxDartBatchLines.getEqxDartBatchLines().add(eqxDartBatchLines);
		eqxDartBatch.setListOfEqxDartBatchLines(listOfEqxDartBatchLines );
		listOfEqxDartBatchIo.getEqxDartBatch().add(eqxDartBatch);
		upsertSpcDARTSpcBatchInput.setListOfEqxDartBatchIo(listOfEqxDartBatchIo);
		UpsertSpcDARTSpcBatchOutput batchOutPut = siebelBatchService.upsertSpcDARTSpcBatch(upsertSpcDARTSpcBatchInput );
		     
		return gson.toJson(batchOutPut);
	}
*/}
