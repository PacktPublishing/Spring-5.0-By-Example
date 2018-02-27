package springfive.airline.airlinebooking.domain.service;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.Flight;
import springfive.airline.airlinebooking.domain.Payment;
import springfive.airline.airlinebooking.domain.Plane;
import springfive.airline.airlinebooking.domain.exception.AlreadyBookedException;
import springfive.airline.airlinebooking.domain.exception.SeatNotAvailableOnPlaneException;
import springfive.airline.airlinebooking.domain.fare.Fare;
import springfive.airline.airlinebooking.domain.fare.Reservation;
import springfive.airline.airlinebooking.domain.payment.PaymentResponse;
import springfive.airline.airlinebooking.domain.payment.RequestPayment;
import springfive.airline.airlinebooking.domain.payment.RequestPayment.FlightInfo;
import springfive.airline.airlinebooking.domain.repository.BookingRepository;
import springfive.airline.airlinebooking.domain.service.data.TotalBooked;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  private final FareService fareService;

  private final PlaneService planeService;

  private final PaymentRequesterService paymentRequesterService;

  private final FlightService flightService;

  public BookingService(BookingRepository bookingRepository,
      FareService fareService,
      PlaneService planeService,
      PaymentRequesterService paymentRequesterService,
      FlightService flightService) {
    this.bookingRepository = bookingRepository;
    this.fareService = fareService;
    this.planeService = planeService;
    this.paymentRequesterService = paymentRequesterService;
    this.flightService = flightService;
  }

  public Mono<Booking> updatePayment(PaymentResponse paymentResponse){
    final Payment payment = Payment.builder().id(paymentResponse.getId())
        .transactionId(paymentResponse.getTransactionId())
        .status(paymentResponse.getStatus()).value(paymentResponse.getValue()).build();

    return this.bookingRepository.findById(paymentResponse.getBookingId()).flatMap(record ->{
      record.setPayment(payment);
      return this.bookingRepository.save(record);
    });
  }

  public Flux<Booking> bookingsOfFlight(@NonNull String flightId){
    return bookingRepository.findByFlightId(flightId);
  }

  public Mono<TotalBooked> totalBooked(@NonNull String flightId){
    return Mono.just(bookingRepository.findByFlightId(flightId)
        .toStream()
          .mapToLong(Booking::booked)
          .sum())
        .flatMap(value -> Mono.just(TotalBooked.builder().total(value).build()));
  }

  public Mono<Booking> byId(String id){
    return this.bookingRepository.findById(id);
  }

  public Mono<Void> cancelBooking(String id){
    return this.bookingRepository.deleteById(id);
  }

  public Mono<Booking> newBooking(String fareId){
    return this.fareService.fare(fareId)
        .flatMap(fare -> Mono.zip(Mono.just(fare),this.planeService.plane(fare.getFlight().getPlane().getId()),this.bookingRepository.findByFlightId(fare.getFlight().getId()).collectList(),this.flightService.flight(fare.getFlight().getId())))
        .flatMap(data ->{
          final Fare fare = data.getT1();
          final Plane plane = data.getT2();
          final List<Booking> bookings = data.getT3();
          final Flight flight = data.getT4();

          fare.getReservations().forEach(reservation -> {

            final boolean alreadyRegistered = bookings.stream()
                .anyMatch(booking -> booking.getSeats().contains(reservation.getSeat()));

            final boolean seatAvailableOnPlane = plane.exist(reservation.getSeat());
            if(!seatAvailableOnPlane){
              throw SeatNotAvailableOnPlaneException.builder().flight(fare.getFlight()).plane(plane).seat(reservation.getSeat()).build();
            }

            if(alreadyRegistered){
              throw AlreadyBookedException.builder().flight(fare.getFlight()).seat(reservation.getSeat()).build();
            }
          });

          return Mono.just(Booking.builder().seats(fare.getReservations().stream().map(Reservation::getSeat).collect(toSet())).flight(flight).fare(fare).build());

        }).flatMap(this.bookingRepository::save).flatMap(booking -> {
          final RequestPayment requestPayment = RequestPayment.builder().bookingId(booking.getId())
              .createdAt(LocalDateTime.now().toString()).value(booking.total())
              .flightInfo(
                  FlightInfo.builder().id(booking.getFlight().getId())
                      .number(booking.getFlight().getNumber()).build()).build();
          return this.paymentRequesterService.requestPayment(requestPayment);
        }).flatMap(requestPayment -> this.bookingRepository.findById(requestPayment.getBookingId()));
  }

}
