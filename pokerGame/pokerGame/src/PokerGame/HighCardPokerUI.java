//go to src folder
//javac PokerGame/HighCardPokerUI.java
//java PokerGame.HighCardPokerUI

package PokerGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Arrays;

public class HighCardPokerUI extends JFrame {

    private int[][] p1Cards = new int[5][2]; // [value, suit]
    private int[][] p2Cards = new int[5][2];

    private String[] values = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "J", "Q", "K", "A"
    };

    private String[] suits = {"♥", "♦", "♣", "♠"};

    private String result = "Click Deal";

    public HighCardPokerUI() {
        setTitle("Poker Cards UI");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton deal = new JButton("Deal Cards");
        deal.addActionListener(e -> {
            dealCards();
            repaint();
        });

        add(deal, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void dealCards() {
        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            p1Cards[i][0] = rand.nextInt(13);
            p1Cards[i][1] = rand.nextInt(4);

            p2Cards[i][0] = rand.nextInt(13);
            p2Cards[i][1] = rand.nextInt(4);
        }

        Arrays.sort(p1Cards, (a, b) -> a[0] - b[0]);
        Arrays.sort(p2Cards, (a, b) -> a[0] - b[0]);

        int p1Best = p1Cards[4][0];
        int p2Best = p2Cards[4][0];

        if (p1Best > p2Best) result = "Player 1 Wins!";
        else if (p2Best > p1Best) result = "Player 2 Wins!";
        else result = "Tie!";
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawPlayer(g, p1Cards, 100, 120, "Player 1");
        drawPlayer(g, p2Cards, 100, 300, "Player 2");

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(result, 300, 450);
    }

    private void drawPlayer(Graphics g, int[][] cards, int startX, int startY, String name) {

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(name, startX, startY - 20);

        for (int i = 0; i < 5; i++) {
            int x = startX + i * 100;
            int y = startY;

            drawCard(g, x, y, cards[i][0], cards[i][1]);
        }
    }

    private void drawCard(Graphics g, int x, int y, int value, int suit) {

        // card shape
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 80, 120);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 80, 120);

        // card text
        String text = values[value] + " " + suits[suit];

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(text, x + 20, y + 60);
    }

    public static void main(String[] args) {
        new HighCardPokerUI();
    }
}
