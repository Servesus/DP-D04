
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfileRepository;
import domain.Profile;

@Service
@Transactional
public class ProfileService {

	@Autowired
	private ProfileRepository	profileRepository;


	public Profile create() {
		Profile result;
		result = new Profile();
		return result;
	}

	public Collection<Profile> findAll() {
		Collection<Profile> result;
		Assert.notNull(this.profileRepository);
		result = this.profileRepository.findAll();
		return result;
	}

	public Profile findOne(final int profileId) {
		Profile result;
		Assert.notNull(this.profileRepository);
		result = this.profileRepository.findOne(profileId);
		return result;
	}

	public Profile save(final Profile profile) {

		Profile result;
		Assert.notNull(profile);
		result = this.profileRepository.save(profile);
		return result;
	}

	public void delete(final Profile profile) {
		Assert.notNull(profile);
		assert profile.getId() != 0;
		Assert.isTrue(this.profileRepository.exists(profile.getId()));
		this.profileRepository.delete(profile);
	}
}
