package ch99;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * https://adamcod.es/2014/05/15/test-doubles-mock-vs-stub.html
 * Spies
 */
public class ApiConsumerTest {

	@Test
	public void it_should_get_all_pages() {
		ThirdPartyApiSpy spy = new ThirdPartyApiSpy();
		ApiConsumer sut = new ApiConsumer(spy);
		sut.fetchAll();

		assertEquals(2, spy.callCount);
	}

	private class ThirdPartyApiSpy implements ThirdPartyApi {

		public int callCount;

		@Override
		public boolean hasMore(Response previousResponse) {
			return this.callCount == 0;
		}

		@Override
		public Response get(int page) {
			this.callCount++;
			return new DummyResponse();
		}
	}

	private class DummyResponse implements Response {

	}

}
