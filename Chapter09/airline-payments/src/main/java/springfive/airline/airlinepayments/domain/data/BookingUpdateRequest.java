package springfive.airline.airlinepayments.domain.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingUpdateRequest {

  String bookingId;

  String newStatus;

  String flightId;

}
