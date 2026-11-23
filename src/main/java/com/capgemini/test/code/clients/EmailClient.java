package com.capgemini.test.code.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "emailClient", url = "${external.service.url}")
public interface EmailClient {

    @PostMapping(value = "/email")
    void enviarEmail(@RequestBody Map<String, String> request);

}
