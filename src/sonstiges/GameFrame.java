package sonstiges;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import panels.DeathScreenPanel;
import panels.GameScreenPanel;
import panels.HomeScreenPanel;

/**
 * Die Klasse baut den Fenster des Betriebssystems aud
 */
public class GameFrame extends JFrame{
	
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel(cardLayout);

	
	/**
	 * Konstruktor der Klasse
	 */
	public GameFrame() {
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		GameScreenPanel gameScreenPanel = new GameScreenPanel(cardLayout, mainPanel);
		DeathScreenPanel deathScreenPanel = new DeathScreenPanel(cardLayout, mainPanel);
		HomeScreenPanel homeScreenPanel = new HomeScreenPanel(cardLayout, mainPanel);
		
        mainPanel.add(homeScreenPanel, "HomeScreen");
        mainPanel.add(gameScreenPanel, "GameScreen");
        mainPanel.add(deathScreenPanel, "DeathScreen");
        
		this.add(mainPanel);
		
		this.setTitle("Snake");
		this.setResizable(false);
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//konfiguriert das Icon, welches oben links angezeigt wird im Fenster
		ImageIcon icon = new ImageIcon(getClass().getResource("/images_icon/snake.png"));
		this.setIconImage(icon.getImage());
		
		this.setVisible(true);
	}

}
