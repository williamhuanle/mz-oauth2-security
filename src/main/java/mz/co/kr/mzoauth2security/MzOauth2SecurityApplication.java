package mz.co.kr.mzoauth2security;

import mz.co.kr.mzoauth2security.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MzOauth2SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MzOauth2SecurityApplication.class, args);
	}

}
