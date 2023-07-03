package com.rhy.bdmp.common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *  *
 *  * @title: RedisConfig
 *  * @Author XuChen
 *  * @Date: 2022/5/20 11:49
 *  redis配置类
 */
@Configuration
@Slf4j
public class RedisConfig {
	/**
	 * 覆盖默认的序列化方式
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		log.info("redis配置已重置");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		//key
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);

		//value
		redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

}
