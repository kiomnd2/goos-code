package org.antop.auctionsniper.unit;

import org.antop.auctionsniper.AuctionEventListener;
import org.antop.auctionsniper.AuctionMessageTranslator;
import org.antop.auctionsniper.end2end.ApplicationRunner;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.antop.auctionsniper.AuctionEventListener.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuctionMessageTranslatorTest {
	public static final Chat UNUSED_CHAT = null;

	private final AuctionEventListener listener = Mockito.mock(AuctionEventListener.class);
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(ApplicationRunner.SNIPER_ID, listener);

	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");

		translator.processMessage(UNUSED_CHAT, message);

		verify(listener, times(1)).auctionClosed();
	}

	@Test
	void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		translator.processMessage(UNUSED_CHAT, message);

		verify(listener, times(1)).currentPrice(192, 7, PriceSource.FromOtherBidder);
	}

	@Test
	void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + ApplicationRunner.SNIPER_ID + ";");
		translator.processMessage(UNUSED_CHAT, message);

		verify(listener, times(1)).currentPrice(234, 5, PriceSource.FromSniper);
	}
}
