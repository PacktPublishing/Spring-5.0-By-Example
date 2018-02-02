package springfive.airline.airlinepayments.domain.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.Payment;
import springfive.airline.airlinepayments.domain.service.PaymentsFinder;

@RestController
@RequestMapping("/payments")
public class PaymentResource {

  private final PaymentsFinder paymentsFinder;

  public PaymentResource(PaymentsFinder paymentsFinder) {
    this.paymentsFinder = paymentsFinder;
  }

  @GetMapping
  public Flux<Payment> payments() {
    return this.paymentsFinder.payments();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Payment>> payment(@PathVariable("id") String id) {
    return this.paymentsFinder.payments(id).map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
