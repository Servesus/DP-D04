
package services;

import java.util.Collection;
import java.util.Collections;

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

	//Supporting service
	private ActorService	actorService;


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
		Assert.isTrue();
		return this.boxRepository.save(box);
	}
	public void delete(final Box box) {
		Assert.isNull(box);
		Assert.isTrue(box.getId() == 0);
		Assert.isTrue(!box.getIsSystem());
		if (!(box.getChildBoxes().isEmpty())) {
			for (final Box b1 : box.getChildBoxes())
				this.boxRepository.delete(b1);
			this.boxRepository.delete(box);
		} else
			this.boxRepository.delete(box);
	}

	//Other business methods

	public Collection<Box> createSystemBoxes() {
		final Collection<Box> res = Collections.emptyList();
		final Box inBox = this.create();
		final Box outBox = this.create();
		final Box trashBox = this.create();
		final Box spamBox = this.create();
		inBox.setName("INBOX");
		outBox.setName("OUTBOX");
		trashBox.setName("TRASHBOX");
		spamBox.setName("SPAMBOX");
		inBox.setIsSystem(true);
		outBox.setIsSystem(true);
		trashBox.setIsSystem(true);
		spamBox.setIsSystem(true);
		res.add(inBox);
		res.add(outBox);
		res.add(trashBox);
		res.add(spamBox);
		return res;
	}

}
