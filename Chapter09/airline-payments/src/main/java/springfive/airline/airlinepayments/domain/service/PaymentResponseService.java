package springfive.airline.airlinepayments.domain.service;

import lombok.NonNull;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.data.PaymentResponse;

@Service
public class PaymentResponseService {

  private final String paymentExchange;

  private final String responsePaymentKey;

  private final AsyncRabbitTemplate asyncRabbitTemplate;

  public PaymentResponseService(@Value("${amqp.payments.exchange.payment}") String paymentExchange,
                                 @Value("${amqp.payments.key.response}") String responsePaymentKey,
                                 AsyncRabbitTemplate asyncRabbitTemplate) {
    this.paymentExchange = paymentExchange;
    this.responsePaymentKey = responsePaymentKey;
    this.asyncRabbitTemplate = asyncRabbitTemplate;
  }

  public Mono<PaymentResponse> responsePayment(@NonNull PaymentResponse paymentResponse){
    final ListenableFuture<PaymentResponse> future = this.asyncRabbitTemplate
        .convertSendAndReceive(this.paymentExchange, this.responsePaymentKey, paymentResponse);
    return Mono.create(monoSink -> future.addCallback(
        monoSink::success,
        monoSink::error));
  }

}
