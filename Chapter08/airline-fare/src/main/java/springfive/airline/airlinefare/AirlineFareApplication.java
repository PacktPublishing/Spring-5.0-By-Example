package springfive.airline.airlinefare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AirlineFareApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlineFareApplication.class, args);
	}

}
