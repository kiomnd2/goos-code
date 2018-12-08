package ch99;

class ApiConsumer {
	private ThirdPartyApi api;

	public ApiConsumer(ThirdPartyApi api) {
		this.api = api;
	}

	public void fetchAll() {
		int page = 0;
		Response resp;
		do {
			resp = api.get(++page);
			// do work
		} while (api.hasMore(resp));

	}
}
