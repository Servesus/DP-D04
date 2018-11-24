
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Box;

@Service
@Transactional
public class BoxService {

	//Managed repository
	@Autowired
	private BoxRepository	boxRepository;


	//Supporting repositories

	//Simple CRUD methods
	public Box create() {
		final Box box = new Box();
		return box;
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int boxID) {
		return this.boxRepository.findOne(boxID);
	}

	public Box save(final Box box) {
		Assert.isNull(box);
		Assert.isTrue(!box.getIsSystem());
		return this.boxRepository.save(box);
	}

	public void delete(final Box box) {
		Assert.isNull(box);
		Assert.isTrue(box.getId() == 0);
		Assert.isTrue(!box.getIsSystem());
		this.boxRepository.delete(box);
	}

	//Other business methods

}
