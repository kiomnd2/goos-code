package org.antop.auctionsniper.unit;

import org.antop.auctionsniper.Auction;
import org.antop.auctionsniper.AuctionSniper;
import org.antop.auctionsniper.SniperListener;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class AuctionSniperTest {

	private final Auction auction = Mockito.mock(Auction.class);
	private final SniperListener sniperListener = Mockito.mock(SniperListener.class);
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);

	@Test
	void reportsLostWhenAuctionCloses() {
		sniper.auctionClosed();
		verify(sniperListener, times(1)).sniperLost();
	}

	@Test
	void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		sniper.currentPrice(price, increment);

		verify(auction, times(1)).bid(price + increment);
		verify(sniperListener, atLeast(1)).sniperBidding();
	}
}
