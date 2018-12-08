package ch99;

import java.util.Objects;

public class User {

	private UserIdentifier identifier;

	public User(UserIdentifier identifier) {
		this.identifier = identifier;
	}

	public UserIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(identifier, user.identifier);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifier);
	}
}
