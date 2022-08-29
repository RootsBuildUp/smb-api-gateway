//package com.smb.springcloudgateway.redis.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//@Configuration
//@PropertySource(name = "application", value = "classpath:config.properties")
//public class OAuthTokenConfig {
//
//	@Value("${redis.hostname}")
//	private String hostName;
//
//	@Value("${redis.port}")
//	private Integer port;
//
//	@Value("${redis.password}")
//	private String password;
//
//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
//		redisStandaloneConfiguration.setPassword(password);
//		return new JedisConnectionFactory(redisStandaloneConfiguration);
//	}
//
//	@Bean
//	RedisTemplate<String, ?> redisTemplate() {
////		Jackson2JsonRedisSerializer<OAuthToken> serializer = new Jackson2JsonRedisSerializer<OAuthToken>(OAuthToken.class);
//
//		RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
//		redisTemplate.setConnectionFactory(jedisConnectionFactory());
//
//		// Data store in Redis server for JSON format
////		redisTemplate.setDefaultSerializer(serializer);
////		redisTemplate.setKeySerializer(new StringRedisSerializer());
////		redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
////		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//		return redisTemplate;
//	}
//	@Bean
//	public RedisSerializer<Object> redisSerializer() {
//
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
//		//dateTime , JSR310 LocalDateTimeSerializer
//		objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
//		//, LocalDateTIme LocalDate , Jackson-data-JSR310
//		objectMapper.registerModule(new JavaTimeModule());
//		//,
//		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
//		GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
//		return new GenericJackson2JsonRedisSerializer(objectMapper);
//
//	}
//}
