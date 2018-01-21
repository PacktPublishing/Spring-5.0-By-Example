package springfive.airline.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;

@Controller
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class EdgeServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(EdgeServerApplication.class, args);
  }

}
