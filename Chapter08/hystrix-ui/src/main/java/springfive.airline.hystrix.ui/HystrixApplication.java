package springfive.airline.hystrix.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixApplication {

  public static void main(String[] args) {
    SpringApplication.run(HystrixApplication.class, args);
  }

}
