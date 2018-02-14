package springfive.cms.domain.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author claudioed on 29/10/17. Project cms
 */
public class NewsTest {

  @Test
  public void shouldBeRevised() {
    News news = new News();
    User reviewer = new User();
    reviewer.setId("1010");
    news.setMandatoryReviewers(Stream.of(reviewer).collect(Collectors.toSet()));
    news.review("1010", "approved");
    assertTrue(news.revised());
  }

  @Test
  public void wrongUserReviewedAndShouldBeNotRevised() {
    News news = new News();
    User reviewer = new User();
    reviewer.setId("2020");
    news.setMandatoryReviewers(Stream.of(reviewer).collect(Collectors.toSet()));
    news.review("1010", "approved");
    assertFalse(news.revised());
  }

  @Test
  public void userNotApprovedAndShouldBeNotRevised() {
    News news = new News();
    User reviewer = new User();
    reviewer.setId("1010");
    news.setMandatoryReviewers(Stream.of(reviewer).collect(Collectors.toSet()));
    news.review("1010", "unapproved");
    assertFalse(news.revised());
  }

}
