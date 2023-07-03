package com.rhy.bdmp.open.modules.assets.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2022/1/12.
 * 自定义属性
 * @author duke
 */
@Configuration
@Data
public class CustomProperties {
    @Value("${custom.file.downUrl}")
    private String fileDownUrl;
}
