package org.antop.auctionsniper;

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
        auction.hasReceivedJoinRequestFromSniper();
        // 4단계: [서버] 경매 폐쇠 알림
        auction.announceClosed();
        // 5단계: [어플] 경매가 낙찰 되었는지 확인
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
