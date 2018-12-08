package ch99;

class CreateUserService {

	private UserRepository repository;

	public CreateUserService(UserRepository userRepository) {
		this.repository = userRepository;
	}

	public void createUser(User user) {
		repository.add(user);
	}

	public void removeUser(User user) {
		repository.delete(user);
	}
}
