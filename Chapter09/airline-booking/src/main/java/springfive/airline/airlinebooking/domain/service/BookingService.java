package springfive.airline.airlinebooking.domain.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.repository.BookingRepository;
import springfive.airline.airlinebooking.domain.service.data.TotalBooked;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  private final FareService fareService;

  public BookingService(BookingRepository bookingRepository,
      FareService fareService) {
    this.bookingRepository = bookingRepository;
    this.fareService = fareService;
  }

  public Flux<Booking> bookings(){
    return this.bookingRepository.findAll();
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

}
