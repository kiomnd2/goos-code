package ch03;

import java.util.Objects;

class Entry {
	private String name;
	private String value;

	public Entry(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Entry entry = (Entry) o;
		return Objects.equals(name, entry.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
