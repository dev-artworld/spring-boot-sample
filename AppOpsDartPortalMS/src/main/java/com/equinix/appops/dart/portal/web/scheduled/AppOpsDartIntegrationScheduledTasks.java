package com.equinix.appops.dart.portal.web.scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;

import com.equinix.appops.common.LogicConstants;
import com.equinix.appops.dart.portal.buisness.AppOpsInitiateDfrBusiness;
import com.equinix.appops.dart.portal.entity.DartSoapAudit;
import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.service.AppOpsDartEditDfrService;
import com.equinix.appops.dart.portal.service.AppOpsInitiateDFRService;
import com.equinix.appops.dart.portal.service.ConfigService;
import com.equinix.appops.dart.portal.service.KafkaSenderService;
/**
 * 
 * @author MM
 *
 */

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name="spring.schedule.job.sblclxsv.retryjob.status", havingValue="ON", matchIfMissing=true)
public class AppOpsDartIntegrationScheduledTasks{
	
	private static final Logger logger = LoggerFactory.getLogger(AppOpsDartIntegrationScheduledTasks.class);
	
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    AppOpsInitiateDFRService initiateDfrService; 
	    
    @Autowired
	AppOpsDartEditDfrService editDfrService;
    
    @Autowired
	ConfigService configService;
    
    @Autowired 
	KafkaSenderService kafkaSenderService;
    
    @Value("${spring.schedule.job.sblclxsv.responsetime.delay}")
    private Long responseTime;

    @Value("${spring.schedule.job.sblclxsv.retry.count}")
    private Long retryCount;
    
    @Scheduled(cron = "${spring.schedule.job.sblclxsv.retryjob.cron}")
    public void scheduleTaskWithCronExpression() {
    	logger.info("Cron Task :clxsblsv: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
     checkDFRAndRetry();
    }

	private void checkDFRAndRetry() {
		try{
			String retryFlag = configService.getValueByKey(LogicConstants.DFR_RETRY_INTEGRATION_FLAG);
			if("ON".equalsIgnoreCase(retryFlag)){
				List<DartSoapAudit> dfrs = initiateDfrService.getRecentAuditsDfrorProduct(responseTime, retryCount);
				logger.info("Total sync error dfr's:"+((dfrs==null||dfrs.isEmpty())?0:dfrs.size()));
				for (DartSoapAudit dfrAudit : dfrs) {
					try{
						logger.error("DFR ID# "+dfrAudit.getDfrId()+" - "+dfrAudit.getRetryCount());
						dfrAudit.setRetryCount(dfrAudit.getRetryCount()+1);
						Boolean isUpdated = initiateDfrService.saveSoapAudit(dfrAudit);
						logger.info("DFR ID#"+dfrAudit.getDfrId()+" retry count:"+dfrAudit.getRetryCount()+" updated:"+isUpdated);
						if(isUpdated && LogicConstants.CLX_PRODUCT.equalsIgnoreCase(dfrAudit.getProduct())){
							kafkaSenderService.sendCLX(new DFRKafkaMessageVO(dfrAudit.getDfrId(),dfrAudit.getRequestId()));
						} else if(isUpdated && LogicConstants.SBL_PRODUCT.equalsIgnoreCase(dfrAudit.getProduct())){
							kafkaSenderService.sendSBL(new DFRKafkaMessageVO(dfrAudit.getDfrId(),dfrAudit.getRequestId()));
						}
					}catch(Exception ex){
						logger.error("unable to push dfr"+dfrAudit.getDfrId(),ex);
					}
				}
			} else{
				logger.info("Unable to retry due to retry flag:"+retryFlag);
			}
		}catch(Exception ex){
			logger.error("unable to get audit dfr's",ex);
		}
		
	}

}
