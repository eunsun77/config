package com.respark.common.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ComponentScan(basePackages = "com.respark.common")
@EnableConfigurationProperties(ResparkSecurityProperties.class)
public class ResparkCommonAutoConfiguration {
}
