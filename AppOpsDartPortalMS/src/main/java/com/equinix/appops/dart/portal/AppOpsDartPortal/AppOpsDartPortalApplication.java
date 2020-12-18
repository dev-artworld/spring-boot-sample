package com.equinix.appops.dart.portal.AppOpsDartPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.siebel.SSLUtilities;

@ComponentScan("com.*")
//@EnableCaching
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class AppOpsDartPortalApplication {

	static {
	    SSLUtilities.trustAllHostnames();
	    SSLUtilities.trustAllHttpsCertificates();
	    System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
	    System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
	    System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
	    System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
	    System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
	    System.setProperty("es.set.netty.runtime.available.processors", "false"); 
	}
	
	public static void main(String[] args) {
		//System.setProperty("server.servlet.context-path","/dart"); 
		SpringApplication springApplication = new SpringApplication(AppOpsDartPortalApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}
	@SuppressWarnings("deprecation")
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}
}
