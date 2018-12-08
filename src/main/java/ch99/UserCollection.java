package ch99;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UserCollection {

	List<User> list = new ArrayList<>();

	public boolean exists(UserIdentifier identifier) {
		for (User user : list) {
			if (user.getIdentifier().equals(identifier)) {
				return true;
			}
		}
		return false;
	}

	public User get(UserIdentifier identifier) {
		for (User user : list) {
			if (user.getIdentifier().equals(identifier)) {
				return user;
			}
		}
		return null;
	}

	public boolean add(User user) {
		return list.add(user);
	}

	public boolean remove(UserIdentifier identifier) {
		Iterator<User> iterator = list.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getIdentifier().equals(identifier)) {
				list.remove(user);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || UserCollection.class.isAssignableFrom(o.getClass()) == false) return false;
		UserCollection that = (UserCollection) o;
		if (this.list.size() != that.list.size()) return false;
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).equals(that.list.get(i)) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(list);
	}
}
