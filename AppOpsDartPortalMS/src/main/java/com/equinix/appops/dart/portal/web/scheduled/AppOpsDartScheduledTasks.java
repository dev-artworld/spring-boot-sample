package com.equinix.appops.dart.portal.web.scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.entity.ApprovalHistory;
import com.equinix.appops.dart.portal.entity.DfrMaster;
import com.equinix.appops.dart.portal.entity.UserInfo;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.EmailSenderService;
import com.equinix.appops.dart.portal.service.UserService;
/**
 * 
 * @author MM
 *
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name="spring.schedule.job.email.status", havingValue="ON", matchIfMissing=true)
public class AppOpsDartScheduledTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AppOpsDartScheduledTasks.class);
	
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
	AppOpsDartEditDfrService editDfrService;
    
    @Autowired
	EmailSenderService emailSenderService;
    
    @Autowired
	ConfigService configService;
    
    @Autowired
    UserService userService;
    
    @Scheduled(cron = "${spring.schedule.job.email.cron}")
    public void scheduleTaskWithCronExpression() {
    	logger.info("Cron Task :DFR Status Checker: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
     checkDFRAndSendAlert();
    }

	private void checkDFRAndSendAlert() {
		try {
			String emailFlag = configService.getValueByKey(LogicConstants.DFR_EMAIL_NOTIFICATION_FLAG);
			HashMap<String, String> dataMap = null;
			if ("ON".equalsIgnoreCase(emailFlag)) {
				List<DfrMaster> dfrs = editDfrService.getDfrMasterCompleted("N");
				logger.info("DFR Emails:" + (dfrs != null ? dfrs.size() : 0));
				for (DfrMaster dfrMaster : dfrs) {
					try {
						DfrMaster dfrMasterObj = editDfrService.getDfrMaster(dfrMaster.getDfrId(), "join");
						String editDfrLink = configService.getValueByKey("EDIT_DFR_LINK");
						String recepientList = editDfrService.getEmailIdByAssignGroup(dfrMaster.getAssignedTeam());
						String templateKey = "DART_REQ_NOTIFICATION";
						dataMap = populatePlaceHolders(dfrMaster, editDfrLink);
						StringBuilder approvedBy = null;
						if (dfrMasterObj != null && CollectionUtils.isNotEmpty(dfrMasterObj.getApprovalHistories())) {
							List<ApprovalHistory> apprHistoryList = dfrMasterObj.getApprovalHistories().stream()
									.filter(historyObj -> "Approved".equalsIgnoreCase(historyObj.getStatus()))
									.collect(Collectors.toList());
							if (apprHistoryList != null && CollectionUtils.isNotEmpty(apprHistoryList)) {
								logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()---> Approved History Size ### "+apprHistoryList.size());
								List<String> oldReceipientList = null;
								if (StringUtils.isNotEmpty(recepientList)) {
									logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()---> Old E-Mail Receipient # "+ recepientList);
									String[] oldMailArray = recepientList.split(",");
									if (oldMailArray != null && oldMailArray.length > 0) {
										oldReceipientList = new ArrayList<String>();
										for (String recipientAddr : oldMailArray) {
											oldReceipientList.add(recipientAddr);
										}
									}
								}
								String approvalEMailStr = null;
								Collections.sort(apprHistoryList,new Comparator<ApprovalHistory>() {
									public int compare (ApprovalHistory histObj1, ApprovalHistory histObj2) {
										if (histObj1.getDfrUpdateDate() == null || histObj2.getDfrUpdateDate() == null) {
											return 0;
										}
										return histObj1.getDfrUpdateDate().compareTo(histObj2.getDfrUpdateDate());
									}
								});
								Collections.reverse(apprHistoryList);
								for (ApprovalHistory histObj : apprHistoryList) {
									approvalEMailStr = editDfrService.getEmailIdByAssignGroup(histObj.getAssignedTeam());
									if (StringUtils.isNotEmpty(approvalEMailStr)) {
										String[] approvalMailArray = approvalEMailStr.split(",");
										if (approvalMailArray != null && approvalMailArray.length > 0) {
											if (oldReceipientList == null) {
												oldReceipientList = new ArrayList<String>();
											}
											for (String recipientAddr : approvalMailArray) {
												if (!oldReceipientList.contains(recipientAddr)) {
													oldReceipientList.add(recipientAddr);
												}
											}
										}
									}
									if (StringUtils.isNotEmpty(histObj.getApprovedBy())
											&& StringUtils.isNotEmpty(histObj.getAssignedTeam())) {
										UserInfo userObj = userService.getUser(histObj.getCreatedBy());
										logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()---> Assigned Team ###"+histObj.getAssignedTeam()+
												"; Approved By ###"+histObj.getApprovedBy());
										if (userObj != null) {
											if (approvedBy == null) {
												approvedBy = new StringBuilder();
											}
											if (approvedBy != null && approvedBy.length() > 0) {
												approvedBy.append("<br>");
											}
											approvedBy.append(histObj.getAssignedTeam() + " - " + userObj.getEmailId()).append("\n");
										}
									}
								}
								if (CollectionUtils.isNotEmpty(oldReceipientList)) {
									recepientList = String.join(",", oldReceipientList);
								}
								logger.info("AppOpsEditDfrBusinessImpl.setApprovedEMailReceipients() # Receipients ---> " + (recepientList != null ? recepientList : ""));
							}
						}
						if (approvedBy != null && approvedBy.length() > 0) {
							dataMap.put("APPROVED_BY", approvedBy.toString());
						} else {
							dataMap.put("APPROVED_BY","");
						}
						logger.info("AppOpsDartScheduledTasks.checkDFRAndSendAlert()--->"+dataMap.get("APPROVED_BY")+";");
						if (dfrMaster.getStatus().equalsIgnoreCase(LogicConstants.CANCELLED)) {
							templateKey = "DART_DFR_CANCEL_NOTIFICATION";
							dataMap.put("USER_ID", dfrMaster.getAssignedTeam());
							dataMap.put("REQUESTED_ITEM",
									null == dfrMaster.getCreatedTeam() ? "" : dfrMaster.getCreatedTeam());
						}
						logger.info("Sending E-Mail for DFR # "+dfrMaster.getDfrId());
						emailSenderService.sendAlert(recepientList, templateKey, dataMap);
						dfrMaster.setEmailFlag("Y");
						editDfrService.saveOrUpdateDfrMaster(dfrMaster);
					} catch (Exception ex) {
						logger.error("Unable to send alert for DFR # " + dfrMaster.getDfrId(), ex);
					}
				}
			} else {
				logger.info("Unable to send E-Mail due to E-Mail Flag:" + emailFlag);
			}
		} catch (Exception ex) {
			logger.error("Unable to get DFR's while sending E-Mail Alert...", ex);
		}
	}

	private HashMap<String, String> populatePlaceHolders(DfrMaster dfrMaster, String editDfrLink) {
		HashMap<String, String> dataMap = new HashMap<String, String>();					
		dataMap.put("USER_ID", dfrMaster.getAssignedTeam());//ASSIGNED GROUP TEAM
		dataMap.put("REQUESTED_ITEM", null==dfrMaster.getAssignedTo()?"":dfrMaster.getAssignedTo());
		dataMap.put("DFR", dfrMaster.getDfrId());
		dataMap.put("SUBJECT", "DART Request "+dfrMaster.getStatus()+" for "+dfrMaster.getDfrId());
		dataMap.put("REQUESTED_BY", null==dfrMaster.getCreatedBy()?"":dfrMaster.getCreatedBy());
		dataMap.put("INCIDENT", null==dfrMaster.getIncident()?"":dfrMaster.getIncident());
		dataMap.put("REGION", dfrMaster.getRegion());
		dataMap.put("NOTES", null==dfrMaster.getNotes()?"":dfrMaster.getNotes());
		dataMap.put("LINK", null==editDfrLink?"":"<a href=\""+editDfrLink+dfrMaster.getDfrId()+"\">click here</a>");
		dataMap.put("STATUS", dfrMaster.getStatus());
		return dataMap;
	}
    
    
}
