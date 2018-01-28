package springfive.airline.mailservice.domain.service.data;

import static java.util.stream.Collectors.toSet;

import java.util.Set;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;

@Data
public class SendgridMail {

  Set<To> personalizations;

  Email from;

  String subject;

  Set<Content> content;

  @Builder
  private static SendgridMail newMail(String to,String from,String subject,String content){
    SendgridMail sendgridMail = new SendgridMail();
    sendgridMail.content = Stream.of(Content.builder().type("text/plain").value(content).build()).collect(toSet());
    sendgridMail.from = Email.builder().email(from).build();
    sendgridMail.subject = subject;
    sendgridMail.personalizations = Stream.of(To.builder().to(Stream.of(Email.builder().email(to).build()).collect(toSet())).build()).collect(toSet());
    return sendgridMail;
  }

}
