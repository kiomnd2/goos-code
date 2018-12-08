package ch99;

import java.util.Objects;
import java.util.UUID;

public class UserIdentifier {

	private UUID uuid;

	public UserIdentifier(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserIdentifier that = (UserIdentifier) o;
		return Objects.equals(uuid, that.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}
}
