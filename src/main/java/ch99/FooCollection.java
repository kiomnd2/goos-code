package ch99;

import java.util.ArrayList;
import java.util.Collection;

class FooCollection {

	private Collection<Foo> c = new ArrayList<>();

	public void add(Foo foo) {
		c.add(foo);
	}

	public int count() {
		return c.size();
	}

	public String joined() {
		String s = "";
		for (Foo foo : c) {
			s += foo.bar();
		}
		return s;
	}
}
