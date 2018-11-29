
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

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
		box.setParentBoxes(new ArrayList<Box>());
		box.setChildBoxes(new ArrayList<Box>());
		box.setMessages(new ArrayList<Message>());
		return box;
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int boxID) {
		return this.boxRepository.findOne(boxID);
	}

	public Box save(final Box box) {
		final Box result = this.boxRepository.save(box);
		final Actor a = this.actorService.getActorLogged();
		final List<Box> boxes = (List<Box>) a.getBoxes();
		Assert.isNull(result);
		if (result.getId() == 0) {
			Assert.isTrue(!result.getIsSystem());
			result.setIsSystem(false);
			boxes.add(result);
			a.setBoxes(boxes);
			this.actorService.save(a);
		} else {
			if (result.getIsSystem()) {
				final Box systemBox = boxes.get(boxes.indexOf(this.findOne(result.getId())));
				Assert.isTrue(systemBox.getName().equals(result.getName()) && result.getChildBoxes().isEmpty() && result.getParentBoxes().isEmpty());
			}
			Assert.isTrue(boxes.contains(result));
			boxes.remove(this.findOne(result.getId()));
			boxes.add(result);
			a.setBoxes(boxes);
			this.actorService.save(a);
		}
		return result;
	}
	public void delete(final Box box) {
		Assert.isNull(box);
		Assert.isTrue(box.getId() != 0);
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
		final List<Box> res = new ArrayList<Box>();
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
		//añadir cajas al result
		res.add(this.boxRepository.save(inBox));
		res.add(this.boxRepository.save(outBox));
		res.add(this.boxRepository.save(trashBox));
		res.add(this.boxRepository.save(spamBox));
		//result
		return res;
	}

	public void makeParentChildBoxes(final Box parents, final Box child) {
		final List<Box> childSParents = (List<Box>) child.getParentBoxes();
		childSParents.add(parents);
		child.setParentBoxes(childSParents);
		this.boxRepository.save(child);
		final List<Box> parentsSChilds = (List<Box>) parents.getChildBoxes();
		parentsSChilds.add(child);
		parents.setChildBoxes(parentsSChilds);
		this.boxRepository.save(parents);
	}

}
