
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service testing
	@Autowired
	private CategoryService	categoryService;


	//TODO Comprobar con login (Falta login)
	@Test
	public void create() {
		Category c;
		c = this.categoryService.create();
		c.setName("categoryTest");

		Assert.notNull(c);
	}

	//TODO
	public void save() {

	}

	//TODO Comprobar con login
	@Test
	public void findOne() {
		Integer id;
		id = this.getEntityId("category1");
		Category c;
		c = this.categoryService.findOne(id);
		Assert.isTrue(c.getName().equals("category1"));
	}

}
