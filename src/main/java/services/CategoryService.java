
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
		Category result;
		result = new Category();
		return result;
	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final Integer categoryId) {
		Assert.isTrue(categoryId != 0);
		return this.categoryRepository.findOne(categoryId);
	}

	public Category save(final Category c) {
		Assert.notNull(c);

		Category result;
		result = this.categoryRepository.save(c);

		return result;
	}

	//TODO
	public void delete(final Category c) {
		Assert.notNull(c);
		if (!(c.getChilds().isEmpty())) {
			for (final Category c1 : c.getChilds())
				this.categoryRepository.delete(c1);
			this.categoryRepository.delete(c);
		} else
			this.categoryRepository.delete(c);
	}
}
