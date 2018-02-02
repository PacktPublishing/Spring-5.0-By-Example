package springfive.airline.airlinepayments.domain.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import springfive.airline.airlinepayments.domain.Payment;

public interface PaymentRepository extends ReactiveCrudRepository<Payment,String>{
}
