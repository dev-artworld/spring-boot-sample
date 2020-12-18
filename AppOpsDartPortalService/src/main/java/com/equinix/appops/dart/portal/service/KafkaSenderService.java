package com.equinix.appops.dart.portal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.model.EmailVO;

@Service
public class KafkaSenderService {

    private final Logger LOG = LoggerFactory.getLogger(KafkaSenderService.class);

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private KafkaTemplate<String, DFRKafkaMessageVO> kafkaTemplate;
    
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private KafkaTemplate<String, EmailVO> emailSenderConfig;
    

    @Value("${spring.kafka.topic}")
    private String topic;
    
    @Value("${spring.kafka.topic.clx.sync}")
    private String topicClx;
    
    @Value("${spring.kafka.topic.sbl.sync}")
    private String topicSBL;
    
    @Value("${spring.kafka.topic.sv.sync}")
    private String topicSV;
    
    @Value("${spring.kafka.topic.email}")
    private String emailTopic;
    
    public void sendCLX(DFRKafkaMessageVO dfrMessage) {
        LOG.info("Sending dfrId='{}' to topic='{}'", dfrMessage, topicClx);
        kafkaTemplate.send(topicClx, dfrMessage);
    }
    
    public void sendSBL(DFRKafkaMessageVO dfrMessage) {
        LOG.info("Sending dfrId='{}' to topic='{}'", dfrMessage, topicSBL);
        kafkaTemplate.send(topicSBL, dfrMessage);
    }
    
    public void sendSV(DFRKafkaMessageVO dfrMessage) {
        LOG.info("Sending dfrId='{}' to topic='{}'", dfrMessage, topicSV);
        kafkaTemplate.send(topicSV, dfrMessage);
    }
    
    public void pushEmail(EmailVO emailVO) {
        LOG.info("Sending message='{}' to topic='{}'", emailVO, emailTopic);
        emailSenderConfig.send(emailTopic, emailVO);
    }
}
