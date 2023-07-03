package com.rhy.bdmp.open.modules.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "login")
public class UserLoginProperties {
    private Map<String,String> params;
}
