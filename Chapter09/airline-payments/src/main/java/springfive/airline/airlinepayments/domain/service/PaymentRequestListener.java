package springfive.airline.airlinepayments.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.Receiver;
import springfive.airline.airlinepayments.domain.data.MailRequest;
import springfive.airline.airlinepayments.domain.data.PaymentRequest;

@Service
public class PaymentRequestListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRequestListener.class);

  private final PaymentService paymentService;

  private final MailNotifier mailNotifier;

  private final BookingNotifier bookingNotifier;

  private final Receiver receiver;

  private final ObjectMapper mapper;

  private final String requestPaymentQueue;

  private final FlightService flightService;

  public PaymentRequestListener(
      PaymentService paymentService,
      MailNotifier mailNotifier,
      BookingNotifier bookingNotifier, Receiver receiver,
      ObjectMapper mapper,
      @Value("${payment.request-payment-queue}") String requestPaymentQueue,
      FlightService flightService) {
    this.paymentService = paymentService;
    this.mailNotifier = mailNotifier;
    this.bookingNotifier = bookingNotifier;
    this.receiver = receiver;
    this.mapper = mapper;
    this.requestPaymentQueue = requestPaymentQueue;
    this.flightService = flightService;
  }

  @PostConstruct
  public void startConsume() {
    this.receiver.consumeAutoAck(this.requestPaymentQueue).subscribe(message -> {
      try {
        val paymentRequest = this.mapper.readValue(new String(message.getBody()), PaymentRequest.class);
        this.paymentService.pay(paymentRequest)
            .flatMap(paymentData -> Mono.just(paymentData.bookingUpdateRequest()))
            .flatMap(this.bookingNotifier::updateBooking)
            .flatMap(data -> this.flightService.flightData(data.getFlightId()))
            .flatMap(flightData -> Mono.just(MailRequest.builder().build()))
            .flatMap(this.mailNotifier::notifyUser)
            .subscribe(data ->{
                LOGGER.info("payment processed");
            });
      } catch (IOException e) {
        throw new RuntimeException("error on deserialize object");
      }
    });
  }

}
