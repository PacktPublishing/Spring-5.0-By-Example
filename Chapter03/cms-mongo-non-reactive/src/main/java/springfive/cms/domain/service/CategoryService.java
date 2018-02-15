package springfive.cms.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import springfive.cms.domain.models.Category;
import springfive.cms.domain.repository.CategoryRepository;
import springfive.cms.domain.vo.CategoryRequest;

/**
 * @author claudioed on 29/10/17. Project cms
 */
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category update(Category category){
    return this.categoryRepository.save(category);
  }

  public Category create(CategoryRequest request){
    Category category = new Category();
    category.setName(request.getName());
    return this.categoryRepository.save(category);
  }

  public void delete(String id){
    final Category category = this.categoryRepository.findOne(id);
    this.categoryRepository.delete(category);
  }

  public List<Category> findAll(){
    return this.categoryRepository.findAll();
  }

  public Category findOne(String id){
    return this.categoryRepository.findOne(id);
  }

}
