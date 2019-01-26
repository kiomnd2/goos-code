package org.antop.auctionsniper.unit;

import org.antop.auctionsniper.Auction;
import org.antop.auctionsniper.AuctionSniper;
import org.antop.auctionsniper.SniperListener;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.antop.auctionsniper.AuctionEventListener.PriceSource;
import static org.mockito.Mockito.*;

public class AuctionSniperTest {

	private final Auction auction = Mockito.mock(Auction.class);
	private final SniperListener sniperListener = Mockito.mock(SniperListener.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);

	@Test
	void reportsLostWhenAuctionClosesImmediately() {
		sniper.auctionClosed();

		verify(sniperListener, atLeast(1)).sniperLost();
	}

	@Test
	void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);

		verify(auction, times(1)).bid(price + increment);
		verify(sniperListener, atLeast(1)).sniperBidding();
	}

	@Test
	void reportsIsWinningWhenCurrentPriceComesFromSniper() {
		sniper.currentPrice(123, 45, PriceSource.FromSniper);

		verify(sniperListener, atLeast(1)).sniperWinning();
	}

	@Test
	void reportsLostIfAuctionClosesWhenBidding() {
		// 다른 입찰자가 입찰
		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		verify(sniperListener, times(1)).sniperBidding();
		// 경매 종료
		sniper.auctionClosed();
		verify(sniperListener, times(1)).sniperLost();
	}

	@Test
	void reportsWonIfAuctionClosesWhenWinning() {
		// 스나이퍼가 입찰
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		// 낙찰 진행
		verify(sniperListener, times(1)).sniperWinning();
		// 경매 종료
		sniper.auctionClosed();
		// 낙찰
		verify(sniperListener, times(1)).sniperWon();
	}
}
