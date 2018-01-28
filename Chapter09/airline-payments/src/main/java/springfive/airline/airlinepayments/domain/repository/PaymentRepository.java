package springfive.airline.airlinepayments.domain.repository;

import org.springframework.data.repository.CrudRepository;
import springfive.airline.airlinepayments.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment,String>{
}
