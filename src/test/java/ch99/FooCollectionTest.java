package ch99;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * https://adamcod.es/2014/05/15/test-doubles-mock-vs-stub.html
 * Dummies
 * Stubs
 * Mocks
 */
public class FooCollectionTest {

	@Test
	public void it_should_maintain_a_count() {
		FooCollection sut = new FooCollection();
		sut.add(new FooDummy());
		sut.add(new FooDummy());

		assertEquals(2, sut.count());
	}

	@Test
	public void it_should_return_joined_bars() {
		FooCollection sut = new FooCollection();
		sut.add(new FooStub());
		sut.add(new FooStub());

		assertEquals("bazbaz", sut.joined());
	}

	@Test
	public void it_should_return_joined_bars_by_mock() {
		Foo fooMock = mock(Foo.class); // instance
		when(fooMock.bar()).thenReturn("baz", "qux"); // behaviour

		FooCollection sut = new FooCollection();
		sut.add(fooMock); // baz
		sut.add(fooMock); // qux
		sut.add(fooMock); // qux?

		assertEquals("bazqux", sut.joined());
		verify(fooMock, times(2)).bar(); // verify
	}

	// dummy
	private class FooDummy implements Foo {

		@Override
		public String bar() {
			return null;
		}
	}

	// Stub
	private class FooStub implements Foo {
		@Override
		public String bar() {
			return "baz";
		}
	}
}
