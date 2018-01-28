package springfive.airline.airlinepayments.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import springfive.airline.airlinepayments.domain.data.BookingUpdateRequest;

@Data
@Document(collection = "payments")
public class Payment {

  String id;

  LocalDateTime createdAt;

  Amount amount;

  Booking booking;

  String status;

  public BookingUpdateRequest bookingUpdateRequest(){
    return BookingUpdateRequest.builder().bookingId(this.booking.id).newStatus(status).build();
  }

}
