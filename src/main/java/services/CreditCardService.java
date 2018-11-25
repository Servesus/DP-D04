package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CreditCard;

import repositories.CreditCardRepository;


@Service
@Transactional
public class CreditCardService {
	
	//Managed repositories
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	//CRUD methods
	
	public CreditCard create(){
		CreditCard result;
		
		result= new CreditCard();
		
		return result;
	}
	
	public CreditCard save(CreditCard creditCard){
		Assert.notNull(creditCard);
		
		CreditCard result;
		
		result= creditCardRepository.save(creditCard);
		
		return result;
	}
	
	public void delete(CreditCard creditCard){
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() != 0);
		Assert.isTrue(creditCardRepository.exists(creditCard.getId()));
		
		creditCardRepository.delete(creditCard);
	}

}
