package springfive.airline.gateway.infra.route;


import java.util.function.Function;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleRoute {

  private Function<PredicateSpec, Builder> addCustomHeader = predicateSpec -> predicateSpec
      .path("/headers")
      .addRequestHeader("Book", "Spring 5.0 By Example")
      .uri("http://httpbin.org:80");

  @Bean
  public RouteLocator sample(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("custom-request-header", addCustomHeader)
        .route("add-query-param", r -> r.path("/get").addRequestParameter("book", "spring5.0")
            .uri("http://httpbin.org:80"))
        .route("response-headers", (r) -> r.path("/response-headers")
            .addResponseHeader("book","spring5.0")
            .uri("http://httpbin.org:80"))
        .route("combine-and-change", (r) -> r.path("/anything").and().header("access-key","AAA")
            .addResponseHeader("access-key","BBB")
            .uri("http://httpbin.org:80"))
        .build();
  }

}
