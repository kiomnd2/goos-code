package org.antop.auctionsniper;

import org.antop.auctionsniper.ui.MainWindow;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

	public static final String MAIN_WINDOW_NAME = "auction-sniper-main";
	private MainWindow ui;
	private Chat notToBeGCd;

	public Main() throws Exception {
		startUserInterface();
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();
		main.joinAuction(connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]), args[ARG_ITEM_ID]);
	}

	private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {

		disconnectWhenUICloses(connection);

		Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), null);
		// don't GC target
		this.notToBeGCd = chat;

		Auction auction = new XMPPAuction(chat);
		chat.addMessageListener(new AuctionMessageTranslator(
				connection.getUser(),
				new AuctionSniper(auction, new SniperStateDisplayer())));
		auction.join();
	}

	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}


	private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		return connection;
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
	}

	private class SniperStateDisplayer implements SniperListener {

		@Override
		public void sniperLost() {
			showStatus(MainWindow.STATUS_LOST);
		}

		@Override
		public void sniperBidding() {
			showStatus(MainWindow.STATUS_BIDDING);
		}

		@Override
		public void sniperWinning() {
			showStatus(MainWindow.STATUS_WINNING);
		}

		@Override
		public void sniperWon() {
			showStatus(MainWindow.STATUS_WON);
		}

		private void showStatus(final String status) {
			SwingUtilities.invokeLater(() -> ui.showStatus(status));
		}
	}

}
