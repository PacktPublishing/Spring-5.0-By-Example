package springfive.airline.airlinepayments.domain.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.Payment;
import springfive.airline.airlinepayments.domain.repository.PaymentRepository;

@Service
public class PaymentsFinder {

  private final PaymentRepository paymentRepository;

  public PaymentsFinder(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Flux<Payment> payments(){
    return this.paymentRepository.findAll();
  }

  public Mono<Payment> payments(String id){
    return this.paymentRepository.findById(id);
  }

}
