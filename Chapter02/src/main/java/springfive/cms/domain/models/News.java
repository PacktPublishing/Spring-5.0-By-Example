package springfive.cms.domain.models;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 * @author claudioed on 28/10/17. Project cms
 */
@Data
public class News {

  String id;

  String title;

  String content;

  User author;

  Set<User> mandatoryReviewers = new HashSet<>();

  Set<Review> reviewers = new HashSet<>();

  Set<Category> categories = new HashSet<>();

  Set<Tag> tags = new HashSet<>();

  public Boolean revised() {
    return this.mandatoryReviewers.stream().allMatch(reviewer -> this.reviewers.stream()
        .anyMatch(review -> reviewer.id.equals(review.userId) && "approved".equals(review.status)));
  }

  public Review review(String userId,String status){
    final Review review = new Review(userId, status);
    this.reviewers.add(review);
    return review;
  }

}