package com.equinix.appops.dart.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equinix.appops.dart.portal.model.DFRKafkaMessageVO;
import com.equinix.appops.dart.portal.service.KafkaSenderService;

@RestController
@RequestMapping("/restservice/v1.0/")
public class KafkaSenderController {

    @Autowired
    private KafkaSenderService senderService;

    @GetMapping("test/{number}")
    public void test(@PathVariable(name="number") String number) {
       // senderService.send(new DFRKafkaMessageVO(dfrId));
    //TODO: test later.
    }
}
