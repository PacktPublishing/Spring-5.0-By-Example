package springfive.airline.airlinepayments.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import springfive.airline.airlinepayments.domain.data.RequestPayment;

@Service
@Slf4j
public class PaymentRequestListener {

  private final PaymentService paymentService;

  public PaymentRequestListener(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @RabbitListener(id = "payment-request-processor-listener",queues = "${amqp.payments.queue.request}")
  public void processPaymentResponse(RequestPayment requestPayment){
    this.paymentService.pay(requestPayment).subscribe(payment -> {
      log.info("Payment processed: " + payment.getId());
    });
  }

}
