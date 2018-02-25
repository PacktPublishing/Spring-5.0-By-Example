package springfive.airline.airlinefare.domain.service;

import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import springfive.airline.airlinefare.domain.Booking;
import springfive.airline.airlinefare.domain.Fare;
import springfive.airline.airlinefare.domain.FareQuery;
import springfive.airline.airlinefare.domain.flight.Flight;
import springfive.airline.airlinefare.domain.Reservation;
import springfive.airline.airlinefare.domain.rules.FareContext;
import springfive.airline.airlinefare.domain.rules.OccupationRule;
import springfive.airline.airlinefare.domain.rules.TimeTaxRule;
import springfive.airline.airlinefare.domain.repository.FareRepository;

@Service
public class FareService {

  private final BookingService bookingService;

  private final FlightService flightService;

  private final FareRepository fareRepository;

  public FareService(BookingService bookingService,
      FlightService flightService,
      FareRepository fareRepository) {
    this.bookingService = bookingService;
    this.flightService = flightService;
    this.fareRepository = fareRepository;
  }

  private Mono<Fare> value(FareQuery fareQuery) {
    final Mono<Flight> flight = this.flightService.flight(fareQuery.getFlight().getId());
    final Mono<Set<Booking>> bookings = this.bookingService
        .bookingOfFlight(fareQuery.getFlight().getId());
    return Mono.zip(flight, bookings, (answerOne, answerTwo) ->
        new FareContext(answerOne, answerTwo, answerOne.getPlane(),
            fareQuery.getReservations()))
        .flatMap(data -> {
          final BigDecimal occupationTax = OccupationRule.builder().bookings(data.getBookings())
              .reservations(fareQuery.getReservations()).build().tax(data.getPlane());
          final BigDecimal timeTax = TimeTaxRule.builder().requestedAt(LocalDateTime.now())
              .build()
              .tax(data.getFlight());
          val reservations = fareQuery.getReservations().stream().map(element -> {
            final Optional<BigDecimal> regularPrice = data.getFlight()
                .categoryPrice(element.getSeat().categoryId());
            if (regularPrice.isPresent()) {
              final BigDecimal additionalTaxes = occupationTax.divide(BigDecimal.valueOf(100))
                  .add(timeTax.divide(BigDecimal.valueOf(100)));

              final BigDecimal additionalValue = regularPrice.get().multiply(additionalTaxes);
              return Reservation.builder().passenger(element.getPassenger()).seat(element.getSeat())
                  .price(regularPrice.get().add(additionalValue)).build();
            } else {
              throw new IllegalArgumentException("Invalid class for tax");
            }
          }).collect(toSet());
          return Mono.just(Fare.builder().id(UUID.randomUUID().toString()).flight(data.flightInfo())
              .validUntil(LocalDateTime.now().plusMinutes(15)).reservations(reservations).build());
        });
  }

  public Mono<Fare> requestFare(FareQuery fareQuery){
    return this.value(fareQuery).flatMap(this.fareRepository::create).flatMap(Mono::just);
  }

  public Mono<Fare> fare(String id){
    return this.fareRepository.fare(id);
  }

}
