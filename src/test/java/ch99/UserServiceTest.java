package ch99;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * ttps://adamcod.es/2014/05/15/test-doubles-mock-vs-stub.html
 * Fakes
 */
public class UserServiceTest {

	@Test
	public void it_should_save_a_new_user() {
		UserRepository userRepository = new InMemoryUserRepository();
		CreateUserService sut = new CreateUserService(userRepository);
		sut.createUser(new UserRequestStub());

		Assert.assertEquals(new UserCollectionStub(), userRepository.fetchAll());
	}

	@Test
	public void it_should_delete_user() {
		UserRepository userRepository = new InMemoryUserRepository();
		CreateUserService sut = new CreateUserService(userRepository);
		sut.createUser(new UserRequestStub());

		User user = new UserRequestStub("99467f99-8c79-4472-b12b-c5032c6c02b1");
		sut.createUser(user);
		sut.removeUser(user);

		Assert.assertEquals(new UserCollectionStub(), userRepository.fetchAll());
	}

	/**
	 * Fake
	 */
	private class InMemoryUserRepository implements UserRepository {

		private UserCollection users = new UserCollection();

		@Override
		public User load(UserIdentifier identifier) {
			if (!this.users.exists(identifier)) {
				throw new InvalidUserException();
			}

			return this.users.get(identifier);
		}

		@Override
		public User find(UserIdentifier identifier) {
			if (!this.users.exists(identifier)) {
				return null;
			}

			return this.users.get(identifier);
		}

		@Override
		public UserCollection fetchAll() {
			return this.users;
		}

		@Override
		public boolean add(User user) {
			return this.users.add(user);
		}

		@Override
		public boolean delete(User user) {
			return this.delete(user.getIdentifier());
		}

		@Override
		public boolean delete(UserIdentifier identifier) {
			return this.users.remove(identifier);
		}
	}

	/**
	 * Stub
	 */
	private class UserRequestStub extends User {
		public UserRequestStub() {
			super(new UserIdentifier(UUID.fromString("cfabb179-95b1-4d01-a1b1-6b8e3968af46")));
		}

		public UserRequestStub(String uuid) {
			super(new UserIdentifier(UUID.fromString(uuid)));
		}
	}

	/**
	 * Stub
	 */
	private class UserCollectionStub extends UserCollection {

		public UserCollectionStub() {
			add(new UserRequestStub());
		}

	}
}
