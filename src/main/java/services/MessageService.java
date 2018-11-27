
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository	messageRepository;
	//Services
	@Autowired
	private ActorService		actorService;


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
		final UserAccount u = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccount(u);
		result.setSender(sender);
		return result;
	}

	public Message save(final Message message) {
		//Asserts y sacar sender y recipient
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);
		final Actor sender = this.actorService.getActorLogged();
		final Collection<Actor> recipients = message.getRecipient();
		for (int i = 0; i < recipients.size() - 1; i++) {
			final Actor a = (Actor) recipients.toArray()[i];
			Assert.isTrue(a.getId() == 0);
		}
		//Set sender message
		message.setSender(sender);
		//meter message outbox sender
		final Box outBoxS = (Box) sender.getBoxes().toArray()[1];
		outBoxS.getMessages().add(message);
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
			for (int i = 0; i < recipients.size() - 1; i++) {
				final Actor a = (Actor) recipients.toArray()[i];
				final Box spamBoxRx = (Box) a.getBoxes().toArray()[3];
				spamBoxRx.getMessages().add(message);
			}
		else
			for (int i = 0; i < recipients.size() - 1; i++) {
				final Actor a = (Actor) recipients.toArray()[i];
				final Box inboxBoxRx = (Box) a.getBoxes().toArray()[0];
				inboxBoxRx.getMessages().add(message);
			}
		//Guardar mensaje en BD
		return this.messageRepository.save(message);
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		this.messageRepository.delete(message);
	}

	//Other business methods

	/*
	 * public void sendMessage(final Message message){
	 * final Box senderBoxes = (Box) message.getSender().getBoxes().toArray()[1];
	 * senderBoxes.set
	 * 
	 * }
	 */
}
