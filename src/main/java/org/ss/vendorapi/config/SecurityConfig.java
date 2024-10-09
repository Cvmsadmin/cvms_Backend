package org.ss.vendorapi.config;
 
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ss.vendorapi.advice.DecryptionRequestAdvice;
import org.ss.vendorapi.security.CorsFilter;
import org.ss.vendorapi.security.JwtAuthenticationEntryPoint;
import org.ss.vendorapi.security.JwtAuthenticationFilter;
 
@Configuration

@EnableWebSecurity

@Order(1)

public class SecurityConfig {
 
	@Autowired

	private JwtAuthenticationEntryPoint point;

	@Autowired

	private UserDetailsService userDetailsService;

 
	

	 @Autowired

	 private CorsFilter corsFilter;

	 @Autowired

	 private DecryptionRequestAdvice decryptionRequestAdvice;

	@Bean

    PasswordEncoder passwordEncoder() {

        return new EncryptSecurityUtil();

    }


	@Bean

    JwtAuthenticationFilter privateApiJwtFilter() {

        return new JwtAuthenticationFilter();

    }

	private static final String[] WHITE_LIST_URLS = {

			"/v2/**",

			"/Payment/*",

			"/v2/api/**",

			"/v3/**"

			};

	@Bean

	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 
 
//        http.authorizeHttpRequests(requests -> requests.antMatchers("/v2/api/acountCreation").hasRole("End Consumer")

//                .antMatchers("/utilityUser").hasRole("Utility User")

//                .anyRequest().authenticated()).formLogin(login -> login.permitAll()).logout(logout -> logout.permitAll());
 
        // v1 is for private API's

        // v2 is for public API's

		http.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(auth -> auth.requestMatchers(WHITE_LIST_URLS).permitAll()

						.requestMatchers("/v1/**").authenticated().anyRequest().authenticated())

				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

 
			http.addFilterBefore(privateApiJwtFilter(), UsernamePasswordAuthenticationFilter.class)

			.addFilterBefore(corsFilter, JwtAuthenticationFilter.class)

			.addFilterBefore(decryptionRequestAdvice,CorsFilter.class);

		return http.build();

	}
 
	

	@Bean

	DaoAuthenticationProvider doDaoAuthenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(userDetailsService);

		provider.setPasswordEncoder(passwordEncoder());

		return provider;

	}


	@Bean

    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {

        return builder.getAuthenticationManager();

    }

//	@Bean

//	@Profile("test")

//    WebSecurityCustomizer webSecurityCustomizer() {

//        return (web) -> web.ignoring()

//        		.anyRequest();

//    }


////	@Bean

//	public JavaMailSender getJavaMailSender() {

//

//		

//      mailSender.setHost("smtp.office365.com");

//      mailSender.setPort(587);

//      mailSender.setUsername("CVMSADMIN@INFINITE.COM");

//      mailSender.setPassword("Admin@cvms");

//

//      Properties props = mailSender.getJavaMailProperties();

//      props.put("mail.transport.protocol", "smtp");

//      props.put("mail.debug", "true");

//      props.put("mail.smtp.auth", "true");

//      props.put("mail.smtp.starttls.enable", "true"); // Ensure TLS is enabled

//      props.put("mail.smtp.from", "CVMSADMIN@INFINITE.COM"); // Set the "from" address

//

//      return mailSender;

//  }

	@Bean

	public JavaMailSender getJavaMailSender() {


	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

//	    mailSender.setHost("10.20.0.1");

	    mailSender.setHost("smtp.office365.com");

	    mailSender.setPort(587);

	    mailSender.setUsername("CVMSADMIN@INFINITE.COM");

	    mailSender.setPassword("Admin@cvms");
 
	    Properties props = mailSender.getJavaMailProperties();

	    props.put("mail.transport.protocol", "smtp");

	    props.put("mail.smtp.auth", "true");

	    props.put("mail.smtp.starttls.enable", "true"); // Enable TLS

	    props.put("mail.smtp.from", "CVMSADMIN@INFINITE.COM");

	    // Add timeouts

	    props.put("mail.smtp.connectiontimeout", "10000"); // 5 seconds

	    props.put("mail.smtp.timeout", "10000"); // 5 seconds

	    props.put("mail.smtp.writetimeout", "10000"); // 5 seconds

	    props.put("mail.debug", "true"); // Enable debug output
 
	    return mailSender;

	}

}

 