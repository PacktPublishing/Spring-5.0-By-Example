package springfive.airline.mailservice.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.mailservice.domain.Mail;
import springfive.airline.mailservice.domain.service.data.SendgridMail;

@Service
public class MailSender {

  private final String apiKey;

  private final String url;

  private final WebClient webClient;

  public MailSender(@Value("${sendgrid.apikey}") String apiKey,
      @Value("${sendgrid.url}") String url,
      WebClient webClient) {
    this.apiKey = apiKey;
    this.webClient = webClient;
    this.url = url;
  }

  public Flux<Void> send(Mail mail){
    final BodyInserter<SendgridMail, ReactiveHttpOutputMessage> body = BodyInserters
        .fromObject(SendgridMail.builder().content(mail.getMessage()).from(mail.getFrom()).to(mail.getTo()).subject(mail.getSubject()).build());
    return this.webClient.mutate().baseUrl(this.url).build().post()
        .uri("/v3/mail/send")
        .body(body)
        .header("Authorization","Bearer " + this.apiKey)
        .header("Content-Type","application/json")
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, clientResponse ->
            Mono.error(new RuntimeException("Error on send email"))
        ).bodyToFlux(Void.class);
  }

}
