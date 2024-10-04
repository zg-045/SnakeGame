package panels;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import sonstiges.SoundManager;

public class DeathScreenPanel extends JPanel {

	private Image background;
	private JPanel mainPanel;
	private CardLayout cardLayout;

	public DeathScreenPanel(CardLayout cardLayout, JPanel mainPanel) {

		erzeugeComponents();

		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
//		SoundManager.playLoop("/nostale_theme_3.wav");
		background = new ImageIcon(getClass().getResource("/images_background/SnakeHintergrund.jpg")).getImage();
	}

	private void erzeugeComponents() {

		// erzeuge Buttons mit Start Playing
		JButton restartButton = new JButton("Restart");
		this.setLayout(null);
		restartButton.setBounds(150, 380, 300, 50);
		this.add(restartButton);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Wechsel zum GameScreen
				cardLayout.show(mainPanel, "GameScreen");
//				SoundManager.stopLoop();
//				SoundManager.playLoop("/sound_backgroundMusic/nostale_theme_2.wav");
				

				GameScreenPanel gamePanel = (GameScreenPanel) mainPanel.getComponent(1);  // Index oder eine Referenz auf das GamePanel

				gamePanel.resetGame();
			}
		});

		// erzeuge Buttons mit Start Playing
		JButton homeButton = new JButton("Home");
		this.setLayout(null);
		homeButton.setBounds(150, 450, 300, 50);
		this.add(homeButton);
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPanel, "HomeScreen");
//				SoundManager.stopLoop();
//				SoundManager.playLoop("/sound_backgroundMusic/nostale_theme_1.wav");
				
				GameScreenPanel gamePanel = (GameScreenPanel) mainPanel.getComponent(1);  // Index oder eine Referenz auf das GamePanel

				gamePanel.resetGame();

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		drawDeathScreen(g);

	}

	private void drawDeathScreen(Graphics g) {
		showScoreboard(g);
	}

	/**
	 * Zeigt den Scoreboard, also den Highscore sowie den aktuellen Score
	 * 
	 * @param g
	 */
	public void showScoreboard(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Game Over", 600 / 4, 600 / 2 - 50);

		// Setzt den Highscore auf den höchsten Wert
		GameScreenPanel.highscore = Math.max(GameScreenPanel.highscore, GameScreenPanel.score);

		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Your Score: " + GameScreenPanel.score, 600 / 3, 600 / 2);
		g.drawString("High Score: " + GameScreenPanel.highscore, 600 / 3, 330);
	}

}
