package com.rhy.bdmp.open.modules.assets.config;

import cn.hutool.json.JSONNull;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author: yanggj
 * @Description: JSON配置
 * @Date: 2021/10/20 14:57
 * @Version: 1.0.0
 */
@Configuration
public class JsonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(JSONNull.class, new JsonSerializer<JSONNull>() {
            @Override
            public void serialize(JSONNull jsonNull, JsonGenerator jsonGenerator
                    , SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNull();
            }
        });
        simpleModule.addDeserializer(JSONNull.class, new JsonDeserializer<JSONNull>() {
            @Override
            public JSONNull deserialize(JsonParser jsonParser
                    , DeserializationContext deserializationContext) {
                return null;
            }
        });
        return new ObjectMapper().registerModule(simpleModule);
    }
}
