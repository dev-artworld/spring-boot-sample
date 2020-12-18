package com.equinix.appops.dart.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equinix.appops.dart.portal.dao.AppOpsDartCommonDAO;
import com.equinix.appops.dart.portal.dao.XmlConfigDao;
import com.equinix.appops.dart.portal.entity.UserPreferences;
import com.equinix.appops.dart.portal.entity.XmlConfig;
import com.equinix.appops.dart.portal.model.NotificationContext;
import com.equinix.appops.dart.portal.model.UserPrefModel;
import com.equinix.appops.dart.portal.model.domain.User;
import com.equinix.appops.dart.portal.model.domain.UserThreadLocal;
import com.equinix.appops.dart.portal.service.AppOpsCommonService;
import com.equinix.appops.dart.portal.service.EmailSenderService;

@Service
@Transactional
public class AppOpsCommonServiceImpl implements AppOpsCommonService {

	Logger logger = LoggerFactory.getLogger(AppOpsCommonServiceImpl.class);

	@Autowired
	XmlConfigDao xmlConfigDao;

	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	AppOpsDartCommonDAO appOpsDartCommonDao;
	
	@Override
	public XmlConfig getXmlConfigByName(String xmlName) {
		return xmlConfigDao.getXmlDataByXmlName(xmlName);
	}

	@Override
	public void sendSampleAlert(String recipients) {
		NotificationContext nc = new NotificationContext();
		nc.setFrom("amp@equinix.com");
		HashMap<String, String> dataMap = getPlaceHolderValues();
		List<String> recepientList = new ArrayList<String>();
		if(StringUtils.isBlank(recipients)){
		recepientList.add("Global-GTS-AMP-Admin@equinix.com");
		} else {
			for (String recEmail : recipients.split(",")) {
				recepientList.add(recEmail);
			}
		}
		nc.setAudiencelist(recepientList);
		nc.setNotifymap(dataMap);

		nc.setTemplatekey("DART_TEST_NOTIFICATION");			
		emailSenderService.publishEmail(nc);

	}

	private HashMap<String, String> getPlaceHolderValues() {		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("USER_ID", "Team");
		map.put("IBX_NAME", "TEST");
		map.put("KIOSK_PLACEMENT", "saursharma@equinix.com");
		map.put("UUID","1234567890");
		map.put("TIMESTAMP",new Date().toGMTString());
		return map;

	}
	
	@Override
	public String persistUserPreferences (UserPrefModel userPrefModel) {
		List<UserPreferences> userPrefList = null;
		List<String> attrList = null;
		UserPreferences userPrefObj = null;
		User user = UserThreadLocal.userThreadLocalVar.get();
		/*User user = new User();
		user.setUserId("sukanta");*/
		if (userPrefModel != null && user != null) {
			userPrefList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(userPrefModel.getCage())) {
				attrList = userPrefModel.getCage();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Cage");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getCabinet())) {
				attrList = userPrefModel.getCabinet();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Cabinet");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getAcCircuit())) {
				attrList = userPrefModel.getAcCircuit();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("AC Circuit");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getDcCircuit())) {
				attrList = userPrefModel.getDcCircuit();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("DC Circuit");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getPatchPanel())) {
				attrList = userPrefModel.getPatchPanel();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Patch Panel");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getNetworkCableConnection())) {
				attrList = userPrefModel.getNetworkCableConnection();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Network Cable Connection");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getDemarcationPoint())) {
				attrList = userPrefModel.getDemarcationPoint();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Demarcation Point");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getPrivatePatchPanelEquinix())) {
				attrList = userPrefModel.getPrivatePatchPanelEquinix();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Private Patch Panel (Equinix)");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			if (CollectionUtils.isNotEmpty(userPrefModel.getPrivatePatchPanelCustomer())) {
				attrList = userPrefModel.getPrivatePatchPanelCustomer();
				for (int i = 1; i <= attrList.size(); i++) {
					userPrefObj = new UserPreferences();
					userPrefObj.setUserId(user.getUserId());
					userPrefObj.setAttrName(attrList.get(i-1));
					userPrefObj.setSequeneOrder(i);
					userPrefObj.setProductName("Private Patch Panel (Customer)");
					userPrefObj.setCreatedDate(new Date());
					userPrefList.add(userPrefObj);
				}
			}
			
			if (StringUtils.isNotEmpty(userPrefModel.getSbl())) {
				userPrefObj = new UserPreferences();
				userPrefObj.setUserId(user.getUserId());
				userPrefObj.setAttrName(userPrefModel.getSbl());
				userPrefObj.setSequeneOrder(1);
				userPrefObj.setProductName("SBL");
				userPrefObj.setCreatedDate(new Date());
				userPrefList.add(userPrefObj);
			}
			if (StringUtils.isNotEmpty(userPrefModel.getClx())) {
				userPrefObj = new UserPreferences();
				userPrefObj.setUserId(user.getUserId());
				userPrefObj.setAttrName(userPrefModel.getClx());
				userPrefObj.setSequeneOrder(1);
				userPrefObj.setProductName("CLX");
				userPrefObj.setCreatedDate(new Date());
				userPrefList.add(userPrefObj);
			}
			if (StringUtils.isNotEmpty(userPrefModel.getSv())) {
				userPrefObj = new UserPreferences();
				userPrefObj.setUserId(user.getUserId());
				userPrefObj.setAttrName(userPrefModel.getSv());
				userPrefObj.setSequeneOrder(1);
				userPrefObj.setProductName("SV");
				userPrefObj.setCreatedDate(new Date());
				userPrefList.add(userPrefObj);
			}
			appOpsDartCommonDao.persistUserPreferences(userPrefList, user.getUserId());
		}
		return "Success";
	}
	
	@Override
	public UserPrefModel getUserPreferences() {
		User user = UserThreadLocal.userThreadLocalVar.get();
		List<UserPreferences> userPrefObjColl = null;
		Map<String,List<UserPreferences>> reponseMap = null;
		UserPrefModel userPrefModel = new UserPrefModel();
		/*User user = new User();
		user.setUserId("sukanta");*/
		if (user != null) {
			userPrefObjColl = appOpsDartCommonDao.getUserPreferences(user.getUserId());
			if (CollectionUtils.isNotEmpty(userPrefObjColl)) {
				List<String> attrList = null;
				reponseMap = userPrefObjColl.stream().collect(Collectors.groupingBy(UserPreferences::getProductName));
				if (reponseMap.containsKey("Cage")) {
					userPrefObjColl = reponseMap.get("Cage");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setCage(attrList);
				}
				if (reponseMap.containsKey("Cabinet")) {
					userPrefObjColl = reponseMap.get("Cabinet");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setCabinet(attrList);
				}
				if (reponseMap.containsKey("AC Circuit")) {
					userPrefObjColl = reponseMap.get("AC Circuit");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setAcCircuit(attrList);
				}
				if (reponseMap.containsKey("DC Circuit")) {
					userPrefObjColl = reponseMap.get("DC Circuit");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setDcCircuit(attrList);
				}
				if (reponseMap.containsKey("Patch Panel")) {
					userPrefObjColl = reponseMap.get("Patch Panel");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setPatchPanel(attrList);
				}
				if (reponseMap.containsKey("Network Cable Connection")) {
					userPrefObjColl = reponseMap.get("Network Cable Connection");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setNetworkCableConnection(attrList);
				}
				if (reponseMap.containsKey("Demarcation Point")) {
					userPrefObjColl = reponseMap.get("Demarcation Point");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setDemarcationPoint(attrList);
				}
				if (reponseMap.containsKey("Private Patch Panel (Equinix)")) {
					userPrefObjColl = reponseMap.get("Private Patch Panel (Equinix)");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setPrivatePatchPanelEquinix(attrList);
				}
				if (reponseMap.containsKey("Private Patch Panel (Customer)")) {
					userPrefObjColl = reponseMap.get("Private Patch Panel (Customer)");
					attrList = new ArrayList<>();
					for (UserPreferences userPref : userPrefObjColl) {
						attrList.add(userPref.getAttrName());
					}
					userPrefModel.setPrivatePatchPanelCustomer(attrList);
				}
				
				if (reponseMap.containsKey("SBL")) {
					userPrefObjColl = reponseMap.get("SBL");
					if (CollectionUtils.isNotEmpty(userPrefObjColl)) {
						userPrefModel.setSbl(((UserPreferences) userPrefObjColl.get(0)).getAttrName());
					}
				}
				if (reponseMap.containsKey("CLX")) {
					userPrefObjColl = reponseMap.get("CLX");
					if (CollectionUtils.isNotEmpty(userPrefObjColl)) {
						userPrefModel.setClx(((UserPreferences) userPrefObjColl.get(0)).getAttrName());
					}
				}
				if (reponseMap.containsKey("SV")) {
					userPrefObjColl = reponseMap.get("SV");
					if (CollectionUtils.isNotEmpty(userPrefObjColl)) {
						userPrefModel.setSv(((UserPreferences) userPrefObjColl.get(0)).getAttrName());
					}
				}
			}
		}
		return userPrefModel;
	}

	@Override
	public String checkCascadeFlag(String dfrId) {
		return appOpsDartCommonDao.checkCascadeFlag(dfrId);
	}
	
}



