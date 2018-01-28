package springfive.airline.mailservice.domain;

import lombok.Data;

@Data
public class Mail {

  String from;

  String to;

  String subject;

  String message;

}
