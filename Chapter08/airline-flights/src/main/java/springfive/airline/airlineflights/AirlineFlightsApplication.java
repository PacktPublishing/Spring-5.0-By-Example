package springfive.airline.airlineflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableHystrix
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class AirlineFlightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineFlightsApplication.class, args);
	}

}
