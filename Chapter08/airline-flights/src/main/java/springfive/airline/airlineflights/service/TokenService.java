package springfive.airline.airlineflights.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springfive.airline.airlineflights.infra.oauth.AccessToken;

@Service
public class TokenService {

    private final WebClient webClient;

    private final String clientId;

    private final String clientSecret;

    private final String authService;

    private final String authServiceApiPath;

    public TokenService(WebClient webClient,
                        @Value("${planes.client_id}") String clientId,
                        @Value("${planes.client_secret}")String clientSecret,
                        @Value("${auth.service}") String authService,
                        @Value("${auth.path}") String authServiceApiPath) {
        this.webClient = webClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authService = authService;
        this.authServiceApiPath = authServiceApiPath;
    }

    public Mono<AccessToken> token(){
        return this.webClient.mutate().baseUrl(this.authService + "/" + this.authServiceApiPath).build()
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type","client_credentials"))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Invalid call"))
                ).onStatus(HttpStatus::is5xxServerError, clientResponse ->
                Mono.error(new RuntimeException("Error on server"))
        ).bodyToMono(AccessToken.class);
    }

}
