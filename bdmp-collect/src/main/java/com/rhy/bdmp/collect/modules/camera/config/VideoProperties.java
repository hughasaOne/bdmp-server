package com.rhy.bdmp.collect.modules.camera.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "custom.video")
@Data
public class VideoProperties {
    private Map<String, String> tenglu;
    private Map<String, String> yuntai;
    private Map<String, String> sy;
}
