package com.spinner.www.common.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerInfo {

    @Value("${domain.url}")
    private String serverUrl;

    @Value("${server.port}")
    private int serverPort;

    private String serverUrlWithPort;

    @PostConstruct
    public void init() {
        this.serverUrlWithPort = serverUrl + ":" + serverPort;
    }
}