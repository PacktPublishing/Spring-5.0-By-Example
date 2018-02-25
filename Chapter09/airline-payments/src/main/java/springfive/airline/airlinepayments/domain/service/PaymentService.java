package springfive.airline.airlinepayments.domain.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinepayments.domain.Amount;
import springfive.airline.airlinepayments.domain.Booking;
import springfive.airline.airlinepayments.domain.Payment;
import springfive.airline.airlinepayments.domain.data.PaymentResponse;
import springfive.airline.airlinepayments.domain.data.PaymentStatus;
import springfive.airline.airlinepayments.domain.data.RequestPayment;

@Service
public class PaymentService {

  private final PaymentRegister paymentRegister;

  private final PaymentResponseService paymentResponseService;

  private final PaymentsFinder paymentsFinder;

  public PaymentService(PaymentRegister paymentRegister,
      PaymentResponseService paymentResponseService,
      PaymentsFinder paymentsFinder) {
    this.paymentRegister = paymentRegister;
    this.paymentResponseService = paymentResponseService;
    this.paymentsFinder = paymentsFinder;
  }

  public Mono<Payment> pay(RequestPayment paymentRequest){
    final Payment payment = Payment.builder()
        .amount(Amount.builder().value(paymentRequest.getValue()).build())
        .booking(Booking.builder().id(paymentRequest.getBookingId()).build())
        .status(PaymentStatus.APPROVED.toString())
        .createdAt(LocalDateTime.now()).build();
    return this.paymentRegister.create(payment).flatMap(data -> {
      final PaymentResponse response = PaymentResponse.builder().bookingId(data.getBooking().getId())
          .id(data.getId()).status(PaymentStatus.APPROVED)
          .transactionId(data.getId()).value(data.getAmount().getValue()).build();
      return paymentResponseService.responsePayment(response);
    }).flatMap(response -> paymentsFinder.payments(response.getId()));
  }

}
