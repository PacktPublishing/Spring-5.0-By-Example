package springfive.airline.airlinepayments.infra.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientProducer {

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }

}
