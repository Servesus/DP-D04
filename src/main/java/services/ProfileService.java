
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfileRepository;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private ProfileRepository	profileRepository;
}
