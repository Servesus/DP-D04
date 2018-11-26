
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
	@Autowired
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
		if (box.getId() == 0) {
			box.setIsSystem(false);
			this.actorService.getActorLogged().getBoxes().add(box);
		} else {
			Assert.isTrue(this.actorService.getActorLogged().getBoxes().contains(box));
			this.actorService.getActorLogged().getBoxes().remove(box);
			this.actorService.getActorLogged().getBoxes().add(box);
		}
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
		//crear cajas memoria vacias
		final Box inBox = this.create();
		final Box outBox = this.create();
		final Box trashBox = this.create();
		final Box spamBox = this.create();
		//poner nombres cajas
		inBox.setName("INBOX");
		outBox.setName("OUTBOX");
		trashBox.setName("TRASHBOX");
		spamBox.setName("SPAMBOX");
		//hacer cajas del sistema
		inBox.setIsSystem(true);
		outBox.setIsSystem(true);
		trashBox.setIsSystem(true);
		spamBox.setIsSystem(true);
		//guardar cajas en BD
		this.boxRepository.save(inBox);
		this.boxRepository.save(outBox);
		this.boxRepository.save(trashBox);
		this.boxRepository.save(spamBox);
		//añadir cajas al result
		res.add(inBox);
		res.add(outBox);
		res.add(trashBox);
		res.add(spamBox);
		//result
		return res;
	}

}
