package springfive.airline.airlinebooking.domain.service;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DiscoveryService {

  private final LoadBalancerClient lbClient;

  private final DiscoveryClient dClient;

  public DiscoveryService(LoadBalancerClient lbClient, DiscoveryClient dClient) {
    this.lbClient = lbClient;
    this.dClient = dClient;
  }

  public Flux<String> serviceAddressFor(String service) {
    return Flux.defer(() ->  Flux.just(this.dClient.getInstances(service)).flatMap(srv ->
        Mono.just(this.lbClient.choose(service))
    ).flatMap(serviceInstance ->
        Mono.just(serviceInstance.getUri().toString())
    ));
  }

}
