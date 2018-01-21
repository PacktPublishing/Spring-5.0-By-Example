package springfive.airline.airlinebooking.domain.service;

import static java.util.stream.Collectors.toSet;

import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfive.airline.airlinebooking.domain.Booking;
import springfive.airline.airlinebooking.domain.fare.Reservation;
import springfive.airline.airlinebooking.domain.repository.BookingRepository;
import springfive.airline.airlinebooking.domain.resource.data.BookingRequest;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  private final FareService fareService;

  public BookingService(BookingRepository bookingRepository,
      FareService fareService) {
    this.bookingRepository = bookingRepository;
    this.fareService = fareService;
  }

  public Flux<Booking> byFlightId(String flightId){
    return this.bookingRepository.findByFlightId(flightId);
  }

  public Mono<Booking> create(@NonNull BookingRequest bookingRequest){
    return this.fareService.fare(bookingRequest.getFareId()).flatMap(fare ->{
      val seats =fare.getReservations().stream().map(Reservation::getSeat).collect(toSet());
      val booking = Booking.builder().fare(fare).flight(fare.getFlight()).seats(seats).build();
      return this.bookingRepository.save(booking);
    });
  }

  public Mono<Booking> booking(String id){
    return this.bookingRepository.findById(id);
  }

  public Mono<Void> delete(Booking booking){
    return this.bookingRepository.delete(booking);
  }


}
