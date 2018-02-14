package springfive.cms.reactor;

import java.util.UUID;
import org.junit.Test;
import reactor.core.publisher.Flux;
import springfive.cms.domain.models.Category;

/**
 * @author claudioed on 27/11/17. Project cms
 */
public class SimpleReactorExample {

  @Test
  public void coldBehavior(){
    Category sports = new Category();
    sports.setName("sports");
    Category music = new Category();
    sports.setName("music");
    Flux.just(sports,music)
        .doOnNext(System.out::println);
  }

  @Test
  public void coldBehaviorWithSubscribe(){
    Category sports = new Category();
    sports.setId(UUID.randomUUID().toString());
    sports.setName("sports");
    Category music = new Category();
    music.setId(UUID.randomUUID().toString());
    music.setName("music");
    Flux.just(sports,music)
        .doOnNext(System.out::println)
        .subscribe();
  }

}
