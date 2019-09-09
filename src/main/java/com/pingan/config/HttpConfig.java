package com.pingan.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wx.http")
@Data
public class HttpConfig {

    private String source;

    private String secret;
}
