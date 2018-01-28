package springfive.airline.mailservice.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.rabbitmq.Receiver;
import springfive.airline.mailservice.domain.Mail;

@Service
@Slf4j
public class MailQueueConsumer {

  private final MailSender mailSender;

  private final String mailQueue;

  private final Receiver receiver;

  private final ObjectMapper mapper;

  public MailQueueConsumer(MailSender mailSender, @Value("${mail.queue}") String mailQueue,
      Receiver receiver, ObjectMapper mapper) {
    this.mailSender = mailSender;
    this.mailQueue = mailQueue;
    this.receiver = receiver;
    this.mapper = mapper;
  }

  @PostConstruct
  public void startConsume() {
    this.receiver.consumeAutoAck(this.mailQueue).subscribe(message -> {
      try {
        val mail = this.mapper.readValue(new String(message.getBody()), Mail.class);
        this.mailSender.send(mail).subscribe(data ->{
          log.info("Mail sent successfully");
        });
      } catch (IOException e) {
        throw new RuntimeException("error on deserialize object");
      }
    });
  }

}
