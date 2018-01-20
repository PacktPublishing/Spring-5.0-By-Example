package springfive.airline.airlinebooking.domain.service;

import org.springframework.stereotype.Service;
import springfive.airline.airlinebooking.domain.repository.BookingRepository;

@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  private final FareService fareService;

  public BookingService(BookingRepository bookingRepository,
      FareService fareService) {
    this.bookingRepository = bookingRepository;
    this.fareService = fareService;
  }



}
