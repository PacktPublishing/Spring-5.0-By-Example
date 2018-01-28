package springfive.airline.airlinepayments.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.data.MailRequest;

@Service
public class MailNotifier {

  public Mono<MailRequest> notifyUser(@NonNull MailRequest mailRequest){
    return Mono.empty();
  }

}
