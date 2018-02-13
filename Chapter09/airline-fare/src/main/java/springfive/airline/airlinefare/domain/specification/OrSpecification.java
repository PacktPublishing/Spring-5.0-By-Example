package springfive.airline.airlinefare.domain.specification;

public class OrSpecification<T> extends AbstractSpecification<T> {

  private Specification<T> spec1;

  private Specification<T> spec2;

  public OrSpecification(final Specification<T> spec1, final Specification<T> spec2) {
    this.spec1 = spec1;
    this.spec2 = spec2;
  }

  public boolean isSatisfiedBy(final T t) {
    return spec1.isSatisfiedBy(t) || spec2.isSatisfiedBy(t);
  }

}