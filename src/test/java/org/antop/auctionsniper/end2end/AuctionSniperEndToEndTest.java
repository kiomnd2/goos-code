package org.antop.auctionsniper.end2end;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuctionSniperEndToEndTest {

	@BeforeAll
	public static void init() {
		System.setProperty("com.objogate.wl.keyboard", "GB");
	}

	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();

	/**
	 * 구글 번역: 저격병은 경매가 끝날 때까지 경매에 합류합니다.
	 *
	 * @throws Exception
	 */
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
		// 1단계: [서버] 품목 판매 시작
		auction.startSellingItem();
		// 2단계: [어플] 입찰 시작
		application.startBiddingIn(auction);
		// 3단계: [서버] 스나이퍼로부터 가입 요청을 받았는지 확인
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		// 4단계: [서버] 경매 폐쇠 알림
		auction.announceClosed();
		// 5단계: [어플] 경매가 낙찰 되었는지 확인
		application.showsSniperHasLostAuction();
	}

	@Test
	void sniperMakesAHigherBidButLoses() throws Exception {
		auction.startSellingItem();

		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		// 스나이퍼에게 other bidder가 1000원에 입찰을 했고 다음 입찰가는 +98원이라는 것을 클라이언트에게 전파
		auction.reportPrice(1000, 98, "other bidder");
		// 상태가 Bidding 인지 확인
		application.hasShownSniperIsBidding();
		// 스이나퍼로 1000+98 입찰을 받았는지 확인
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	// https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
	// @After == @AfterEach
	// @AfterClass == @AfterAll
	@AfterEach
	public void stopAuction() {
		auction.stop();
	}

	@AfterEach
	public void stopApplication() {
		application.stop();
	}

}
