
package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
<<<<<<< HEAD
import security.LoginService;
import security.UserAccount;
import domain.Actor;
=======
import domain.Box;
>>>>>>> origin/manuercaximba
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
		Assert.notNull(message);
		final Date currentMoment = new Date();
		message.setSendDate(currentMoment);
		final Message result = this.messageRepository.save(message);
		return result;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

		this.messageRepository.delete(message);
	}

	//Other business methods

	public void sendMessage(final Message message){
		final Box senderBoxes = (Box) message.getSender().getBoxes().toArray()[1];
		senderBoxes.set
		
	}

}
