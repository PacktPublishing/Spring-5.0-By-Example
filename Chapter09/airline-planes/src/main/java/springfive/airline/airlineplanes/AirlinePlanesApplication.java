package springfive.airline.airlineplanes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableZuulProxy
@EnableEurekaClient
@EnableResourceServer
@SpringBootApplication
public class AirlinePlanesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlinePlanesApplication.class, args);
	}

}
