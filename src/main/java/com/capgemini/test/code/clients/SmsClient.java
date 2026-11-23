package com.capgemini.test.code.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "smsClient", url = "${external.service.url}")
public interface SmsClient {

    @PostMapping(value = "/sms")
    void enviarSms(@RequestBody Map<String, String> request);

}