package org.antop.auctionsniper.ui;

import org.antop.auctionsniper.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final String SNIPER_STATUS_NAME = "status-label";

    public static final String STATUS_JOINING = "Joining";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_WINNING = "Winning";

	private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);

        add(sniperStatus);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JLabel createLabel(String initalText) {
        JLabel label = new JLabel(initalText);
        label.setName(SNIPER_STATUS_NAME);
        label.setBorder(new LineBorder(Color.BLACK));
        return label;
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
