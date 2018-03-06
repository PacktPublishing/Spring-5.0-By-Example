package springfive.airline.airlinebooking.infra.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  private final String paymentRequestQueue;

  private final String paymentResponseQueue;

  private final String paymentExchange;

  private final String paymentRequestKey;

  private final String paymentResponseKey;

  public RabbitMQConfiguration(@Value("${amqp.payments.queue.request}") String paymentRequestQueue,
                               @Value("${amqp.payments.queue.response}")String paymentResponseQueue,
                               @Value("${amqp.payments.exchange.payment}")String paymentExchange,
                               @Value("${amqp.payments.key.request}")String paymentRequestKey,
                               @Value("${amqp.payments.key.response}")String paymentResponseKey){
    this.paymentRequestQueue = paymentRequestQueue;
    this.paymentResponseQueue = paymentResponseQueue;
    this.paymentExchange = paymentExchange;
    this.paymentRequestKey = paymentRequestKey;
    this.paymentResponseKey = paymentResponseKey;
  }

  @Bean("paymentExchange")
  public DirectExchange paymentExchange(){
    return new DirectExchange(this.paymentExchange,true,false);
  }

  @Bean("paymentRequestQueue")
  public Queue paymentRequestQueue(){
    return new Queue(this.paymentRequestQueue,true);
  }

  @Bean("paymentResponseQueue")
  public Queue paymentResponseQueue(){
    return new Queue(this.paymentResponseQueue,true);
  }

  @Bean("paymentRequestBinding")
  public Binding paymentRequestBinding(DirectExchange exchange,@Qualifier("paymentRequestQueue") Queue paymentRequestQueue){
    return BindingBuilder.bind(paymentRequestQueue).to(exchange).with(this.paymentRequestKey);
  }

  @Bean("paymentResponseBinding")
  public Binding paymentResponseBinding(DirectExchange exchange,@Qualifier("paymentResponseQueue") Queue paymentResponseQueue){
    return BindingBuilder.bind(paymentResponseQueue).to(exchange).with(this.paymentResponseKey);
  }

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate){
    return new AsyncRabbitTemplate(rabbitTemplate);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory factory,Jackson2JsonMessageConverter messageConverter){
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }

}
