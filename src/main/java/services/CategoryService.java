
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	//Managed Repository
	@Autowired
	private CategoryRepository	categoryRepository;


	//Simple CRUD methods
	//TODO
	public Category create() {
		final Category c = new Category();
		return c;
	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final Integer categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Category save(final Category c) {
		Assert.notNull(c);
		final Category saved = this.categoryRepository.save(c);
		return saved;
	}

	//TODO
	public void delete(final Category c) {
		Assert.notNull(c);
		this.categoryRepository.delete(c);
	}
}
