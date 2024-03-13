package com.techathome.config;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

@Configuration
public class JacksonConfig {

	@Bean("afterburnerModule")
	public Module afterburnerModule() {
		return new AfterburnerModule();
	}
	
//	@Primary
//	@Bean
//	public ObjectMapper objectMapper() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
//		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		mapper.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
//
//		return mapper.registerModule(afterburnerModule());
//
//	}
	
	@Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        		.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"))
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))
                .registerModule(new JavaTimeModule())
                .registerModule(afterburnerModule());
    }
	
	
}
