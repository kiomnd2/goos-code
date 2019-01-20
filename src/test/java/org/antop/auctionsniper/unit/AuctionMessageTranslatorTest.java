package org.antop.auctionsniper.unit;

import org.antop.auctionsniper.AuctionEventListener;
import org.antop.auctionsniper.AuctionMessageTranslator;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AuctionMessageTranslatorTest {
	public static final Chat UNUSED_CHAT = null;

	private final AuctionEventListener listener = Mockito.mock(AuctionEventListener.class);
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: CLOSE;");

		translator.processMessage(UNUSED_CHAT, message);

		verify(listener, times(1)).auctionClosed();
	}

	@Test
	void notifiesBidDetailsWhenCurrentPriceMessageReceived() {
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");

		translator.processMessage(UNUSED_CHAT, message);

		verify(listener, times(1	)).currentPrice(192, 7);
	}
}
