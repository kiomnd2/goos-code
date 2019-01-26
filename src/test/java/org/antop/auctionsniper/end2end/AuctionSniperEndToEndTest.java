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
		// other bidder가 1000원에 입찰을 했고 다음 입찰가는 +98원이라는 것을 클라이언트에게 전파
		// 여기서 스나이퍼는 여기서 1098원일 입찰함
		auction.reportPrice(1000, 98, "other bidder");
		// 상태가 Bidding 인지 확인
		application.hasShownSniperIsBidding();
		// 스이나퍼로부터 1000+98 입찰을 받았는지 확인
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	@Test
	void sniperWinsAnAuctionByBidingHigher() throws Exception {
		// [서버] 경매 시작
		auction.startSellingItem();
		// [ㅇ플] 경매 차여
		application.startBiddingIn(auction);
		// 스나이퍼가 옥션에 참여 했는가?
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		// 다른 입찰자가 1000원에 입찰을 했고 98원을 더 올려 입찰해야 한다고 알림
		// 현재 13장에서 스나이퍼는 바로 1000 + 98 입찰을 한다.
		auction.reportPrice(1000, 98, "other bidder");
		// [어플] 상태가 Bidding 인지 확인
		application.hasShownSniperIsBidding();
		// [서버] 스나이퍼로부터 1098원을 입찰 받았는지 확인
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		// [서버] 모두에게 1098원이 입찰가고 다음 입찰가는 +97원 해야하며 입찰자는 스나이퍼라고 알림
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		// [어플] 상태가 낙찰 진행인지 확인
		application.hasShownSniperIsWinning();
		// [서버] 경매 종료
		auction.announceClosed();
		// [어플] 낙찰 확인
		application.showsSniperHasWonAuction();
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
