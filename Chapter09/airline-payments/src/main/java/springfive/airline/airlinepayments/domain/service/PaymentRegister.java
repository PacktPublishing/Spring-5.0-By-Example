package springfive.airline.airlinepayments.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.Payment;
import springfive.airline.airlinepayments.domain.repository.PaymentRepository;

@Service
public class PaymentRegister {

  private final PaymentRepository paymentRepository;

  public PaymentRegister(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Mono<Payment> create(@NonNull Payment payment){
    return this.paymentRepository.save(payment);
  }

}
