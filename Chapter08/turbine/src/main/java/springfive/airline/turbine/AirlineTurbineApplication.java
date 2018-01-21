package springfive.airline.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@EnableTurbineStream
@SpringBootApplication
public class AirlineTurbineApplication {

	public static void main(String[] args) {
    SpringApplication.run(AirlineTurbineApplication.class, args);
	}

}