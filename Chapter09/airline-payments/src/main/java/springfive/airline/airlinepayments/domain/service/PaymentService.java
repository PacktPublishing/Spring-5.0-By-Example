package springfive.airline.airlinepayments.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Sender;
import springfive.airline.airlinepayments.domain.Payment;
import springfive.airline.airlinepayments.domain.data.PaymentRequest;

@Service
public class PaymentService {

  private final PaymentRegister paymentRegister;

  private final Sender sender;

  private final String responsePaymentQueue;

  public PaymentService(PaymentRegister paymentRegister, Sender sender,
      @Value("${payment.response-payment-queue}") String responsePaymentQueue) {
    this.paymentRegister = paymentRegister;
    this.sender = sender;
    this.responsePaymentQueue = responsePaymentQueue;
  }

  public Mono<Payment> pay(PaymentRequest paymentRequest){
    return Mono.empty();
  }

}
