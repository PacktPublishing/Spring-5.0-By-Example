package springfive.airline.airlinepayments.domain.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MailRequest {

  String from;

  String to;

  String subject;

  String message;

}
