package ch99;

public interface UserRepository {

	User load(UserIdentifier identifier);

	User find(UserIdentifier identifier);

	UserCollection fetchAll();

	boolean add(User user);

	boolean delete(User user);

	boolean delete(UserIdentifier identifier);

}
