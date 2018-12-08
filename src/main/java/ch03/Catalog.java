package ch03;

import java.util.ArrayList;
import java.util.List;

class Catalog {

	private List<Entry> list = new ArrayList<>();

	public void add(Entry entry) {
		if (list.contains(entry)) {
			throw new IllegalArgumentException();
		}
		list.add(entry);
	}

	public boolean contains(Entry entry) {
		for(Entry e : list) {
			if (e.getName().equals(entry.getName())) {
				return true;
			}
		}
		return false;
	}

	public Entry entryFor(String name) {
		for(Entry e : list) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}
}
