package springfive.airline.airlinebooking.domain.service;

import lombok.NonNull;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.payment.RequestPayment;

@Service
public class PaymentRequesterService {

  private final String paymentExchange;

  private final String requestPaymentKey;

  private final AsyncRabbitTemplate asyncRabbitTemplate;


  public PaymentRequesterService(@Value("${amqp.payments.exchange.payment}") String paymentExchange,
      @Value("${amqp.payments.key.request}") String requestPaymentKey,
      AsyncRabbitTemplate asyncRabbitTemplate) {
    this.paymentExchange = paymentExchange;
    this.requestPaymentKey = requestPaymentKey;
    this.asyncRabbitTemplate = asyncRabbitTemplate;
  }

  public Mono<RequestPayment> requestPayment(@NonNull RequestPayment requestPayment){
    final ListenableFuture<RequestPayment> future = this.asyncRabbitTemplate
        .convertSendAndReceive(this.paymentExchange, this.requestPaymentKey, requestPayment);
    return Mono.create(monoSink -> future.addCallback(
        monoSink::success,
        monoSink::error));
  }

}
