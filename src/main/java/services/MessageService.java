
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository		messageRepository;
	//Services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD Methods
	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final Integer arg0) {
		return this.messageRepository.findOne(arg0);
	}

	public Message create() {
		Message result;
		result = new Message();
		final Actor sender = this.actorService.getActorLogged();
		result.setSender(sender);
		result.setRecipient(new ArrayList<Actor>());
		return result;
	}

	public Message save(final Message message) {
		//Asserts y sacar sender y recipient
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		final Actor sender = message.getSender();
		final List<Actor> recipients = (List<Actor>) message.getRecipient();
		for (int i = 0; i < recipients.size() - 1; i++) {
			//final Actor a = (Actor) recipients.toArray()[i];
			final Actor a = recipients.get(i);
			Assert.isTrue(a.getId() == 0);
		}
		//Set sender message
		message.setSender(sender);
		//meter message outbox sender
		final Box outBoxS = (Box) sender.getBoxes().toArray()[1];
		outBoxS.getMessages().add(message);
		this.boxService.save(outBoxS);
		this.actorService.save(sender);
		//lista de palabras spam
		final String[] spam = {
			"sex", "viagra", "cialis", "one million", "you've been selected", "Nigeria", "sexo", "un millón", "ha sido seleccionado"
		};
		//meter message inbox/spambox recipient
		boolean msgIsSpam = false;
		for (int i = 0; i < spam.length; i++)
			if (message.getSubject().contains(spam[i]) || message.getBody().contains(spam[i]))
				msgIsSpam = true;
		if (msgIsSpam)
			for (int i = 0; i < recipients.size(); i++) {
				final Actor a = recipients.get(i);
				final Box spamBoxRx = (Box) a.getBoxes().toArray()[3];
				spamBoxRx.getMessages().add(message);
				this.boxService.save(spamBoxRx);
				this.actorService.save(a);
			}
		else
			for (int i = 0; i < recipients.size(); i++) {
				final Actor a = (Actor) recipients.toArray()[i];
				final Box inboxBoxRx = (Box) a.getBoxes().toArray()[0];
				inboxBoxRx.getMessages().add(message);
				this.boxService.save(inboxBoxRx);
				this.actorService.save(a);
			}
		//Guardar mensaje en BD
		return this.messageRepository.save(message);
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		this.messageRepository.delete(message);
	}

	//Other business methods

	public void moveMessage(final Message message, final Box boxR) {
		//Asserts e inicializaciones
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		final Actor a = message.getSender();
		final List<Box> boxesActor = (List<Box>) a.getBoxes();
		Box originBox = null;
		boolean msgInActor = false;
		boolean boxInActor = false;
		for (int i = 0; i < a.getBoxes().size(); i++) {
			if (boxesActor.get(i).getMessages().contains(message)) {
				originBox = boxesActor.get(i);
				msgInActor = true;
			}
			if (boxesActor.get(i).equals(boxR))
				boxInActor = true;
		}
		Assert.isTrue(!msgInActor || !boxInActor);
		//Mover mensage
		originBox.getMessages().remove(message);
		boxR.getMessages().add(message);
		this.boxService.save(originBox);
		this.boxService.save(boxR);
		this.actorService.save(a);

	}

	public void copyMessage(final Message message, final Box boxR) {
		//Asserts e inicializaciones
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		final Actor a = message.getSender();
		final List<Box> boxesActor = (List<Box>) a.getBoxes();
		Box originBox = null;
		boolean msgInActor = false;
		boolean boxInActor = false;
		for (int i = 0; i < a.getBoxes().size(); i++) {
			if (boxesActor.get(i).getMessages().contains(message)) {
				originBox = boxesActor.get(i);
				msgInActor = true;
			}
			if (boxesActor.get(i).equals(boxR))
				boxInActor = true;
		}
		Assert.isTrue(!msgInActor || !boxInActor);
		//Mover mensage
		boxR.getMessages().add(message);
		this.boxService.save(originBox);
		this.boxService.save(boxR);
		this.actorService.save(a);

	}

	public void deleteMessage(final Message message) {
		//Asserts e inicializaciones
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		final Actor a = message.getSender();
		final List<Box> boxesActor = (List<Box>) a.getBoxes();
		Box originBox = null;
		boolean msgInActor = false;
		for (int i = 0; i < a.getBoxes().size(); i++)
			if (boxesActor.get(i).getMessages().contains(message)) {
				originBox = boxesActor.get(i);
				msgInActor = true;
			}
		Assert.isTrue(!msgInActor);
		//Si no es trashbox, mover a trashbox
		if (boxesActor.get(2) != originBox) {
			originBox.getMessages().remove(message);
			final Box trashBox = boxesActor.get(2);
			trashBox.getMessages().add(message);
			this.boxService.save(originBox);
			this.boxService.save(trashBox);
			this.actorService.save(a);
			//Si trashbox, borrar de actor
		} else
			for (int i = 0; i < a.getBoxes().size(); i++)
				if (boxesActor.get(i).getMessages().contains(message)) {
					final Box boxWithMsg = boxesActor.get(i);
					boxWithMsg.getMessages().remove(message);
					this.boxService.save(boxWithMsg);
					this.boxService.save(a);
				}
	}

	public Message adminMessage(final Message message) {
		//Asserts e inicializaciones
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		//Mandar mensaje a todos los actores del sistema
		final Actor admin = this.administratorService.findOne(this.actorService.getActorLogged().getId());
		final List<Actor> recipient = new ArrayList<Actor>();
		recipient.addAll(this.actorService.findAll());
		recipient.remove(admin);
		message.setSender(admin);
		message.setRecipient(recipient);
		return this.save(message);
	}
}
