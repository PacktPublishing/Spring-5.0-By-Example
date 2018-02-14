package springfive.cms.domain.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfive.cms.domain.exceptions.CategoryNotFoundException;
import springfive.cms.domain.models.Category;
import springfive.cms.domain.repository.CategoryRepository;
import springfive.cms.domain.vo.CategoryRequest;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Transactional
  public Category update(Category category) {
    return this.categoryRepository.save(category);
  }

  @Transactional
  public Category create(CategoryRequest request) {
    Category category = new Category();
    category.setName(request.getName());
    return this.categoryRepository.save(category);
  }

  @Transactional
  public void delete(String id) {
    final Optional<Category> category = this.categoryRepository.findById(id);
    category.ifPresent(this.categoryRepository::delete);
  }

  public List<Category> findAll() {
    return this.categoryRepository.findAll();
  }

  public List<Category> findByName(String name) {
    return this.categoryRepository.findByName(name);
  }

  public List<Category> findByNameStartingWith(String name) {
    return this.categoryRepository.findByNameIgnoreCaseStartingWith(name);
  }

  public Category findOne(String id) {
    final Optional<Category> category = this.categoryRepository.findById(id);
    if (category.isPresent()) {
      return category.get();
    } else {
      throw new CategoryNotFoundException(id);
    }
  }

}
