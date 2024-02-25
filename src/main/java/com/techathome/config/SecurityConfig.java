package com.techathome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.techathome.repository.AccountRepository;
import com.techathome.services.UserInfoUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AccountRepository accountRepository;

    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(accountRepository);
    }

    protected static final String[] ANON_URLS = {
            "/img/**",
            "/static/css/**",
            "/fonts/**",
            "/plugins/**",
            "/js/**",
            "/text/**",
            "/password/reminder/**",
            "/password/reset/**",
            "/registration/**",
            "/login/**"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//    	.authorizeHttpRequests(new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
//			@Override
//			public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry t) {
//				t.requestMatchers(ANON_URLS)
//					.permitAll()
//					.anyRequest()
//					.fullyAuthenticated();
//			}
//		})
        		.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
					@Override
					public void customize(CsrfConfigurer<HttpSecurity> t) {
						t.disable();
					}
				})
                .formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(FormLoginConfigurer<HttpSecurity> t) {
                        t.loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password");
                        // TODO ilerde login olunca başka sayfalara yönlendirmek için buraya
                        // SimpleUrlAuthenticationSuccessHandler sınıfını extend eden bir sınıf verilebilir
//                .successHandler()
//                .failureHandler()
//                .permitAll();
                    }
                })
                .logout(new Customizer<LogoutConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(LogoutConfigurer<HttpSecurity> t) {
                        t.logoutUrl("/logout")
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true);
//					.logoutSuccessHandler()
//					.permitAll();

                    }
                }).headers(new Customizer<HeadersConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(HeadersConfigurer<HttpSecurity> t) {
                        t.cacheControl(new Customizer<HeadersConfigurer<HttpSecurity>.CacheControlConfig>() {
                            @Override
                            public void customize(HeadersConfigurer<HttpSecurity>.CacheControlConfig t) {
                            }
                        });
                    }
                });
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}
