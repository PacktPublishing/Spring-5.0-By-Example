package springfive.airline.airlineecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableHystrix
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@EnableResourceServer
public class AirlineEcommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AirlineEcommerceApplication.class, args);
  }

}
