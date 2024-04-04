package com.techathome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;


/**
 * 
 * @author <a href="mailto:info@agem.com.tr">AGEM Bilisim Yazilim Ltd. Sti.</a>
 *
 */
@Configuration
public class ThymeleafConfig {

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
}
