package springfive.airline.airlinepayments.domain.service;

import org.springframework.stereotype.Service;
import springfive.airline.airlinepayments.domain.repository.PaymentRepository;

@Service
public class PaymentRegister {

  private final PaymentRepository paymentRepository;

  public PaymentRegister(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }


}
