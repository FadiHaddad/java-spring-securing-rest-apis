package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final ResolutionRepository resolutions;
	private final UserRepository users;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository users) {
		this.resolutions = resolutions;
		this.users = users;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

		User admin = new User("admin", "{bcrypt}$2a$10$k89fMRGrFHtYXJGyI0rXb.axmEb7UfAbcKm8/xaHMXXZkFs6s9jIu");
		admin.grantAuthority("ROLE_ADMIN");
		admin.setFullName("Admin Adminson");
		this.users.save(admin);

		User user = new User("user", "{bcrypt}$2a$10$HQEuMykoWpznF.sUEIsOceDYOXPVxb/r50D9wUj2HrG7BSjZ62/7.");
		user.grantAuthority("resolution:read");
		user.grantAuthority("user:read");
		user.grantAuthority("resolution:write");
		user.setFullName("User Userson");
		this.users.save(user);

		User hasread = new User("hasread", "{bcrypt}$2a$10$HQEuMykoWpznF.sUEIsOceDYOXPVxb/r50D9wUj2HrG7BSjZ62/7.");
		hasread.grantAuthority("resolution:read");
		hasread.grantAuthority("user:read");
		hasread.setFullName("Has Read");
		this.users.save(hasread);

		User haswrite = new User("haswrite", "{bcrypt}$2a$10$HQEuMykoWpznF.sUEIsOceDYOXPVxb/r50D9wUj2HrG7BSjZ62/7.");
		haswrite.grantAuthority("resolution:write");
		haswrite.setFullName("Has Write");
		haswrite.addFriend(hasread);
		haswrite.setSubscription("premium");
		this.users.save(haswrite);


	}
}
