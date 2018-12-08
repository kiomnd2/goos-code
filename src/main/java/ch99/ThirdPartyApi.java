package ch99;

interface ThirdPartyApi {

	boolean hasMore(Response previousResponse);

	Response get(int page);

}
