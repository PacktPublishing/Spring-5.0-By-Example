package springfive.airline.airlinebooking.infra.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

  String from;

  String to;

  String subject;

  String message;

}
