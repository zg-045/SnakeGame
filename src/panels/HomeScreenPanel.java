package panels;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import sonstiges.SoundManager;

public class HomeScreenPanel extends JPanel {

	private Image background;
	private JPanel mainPanel;
	private CardLayout cardLayout;
	
	private int FAST = 25;
	private int NORMAL = 50;
	private int SLOW = 150;

	public HomeScreenPanel(CardLayout cardLayout, JPanel mainPanel) {
		
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		erzeugeComponents();
//		SoundManager.playLoop("/sound_backgroundMusic/nostale_theme_1.wav");

	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		drawHomeScreen(g);
	}

	public void drawHomeScreen(Graphics g) {
	
		background = new ImageIcon(getClass().getResource("/images_background/SnakeHintergrund.jpg")).getImage();
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		drawText(g);
	}

	/**
	 * Zeichnet die Willkommens-Anzeige sowie den HighScore.
	 * 
	 * @param g
	 */
	private void drawText(Graphics g) {
		// erzeuge eine Anzeige mit der Aufschrift "Willkommen" und ggf. weitere Bilder
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 50));
				g.drawString("Willkommen", 600 / 4, 150);

				// erzeuge ene Anzeige mit der Aufschrift "Personal Highscore"
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 25));
				g.drawString("Personal Highscore : " + GameScreenPanel.highscore, 600 / 4, 250);
	}

	/**
	 * Zeichnet den Button sowie den Slider
	 */
	private void erzeugeComponents() {

		// erzeuge einen horizontalen Slider mit der man in drei Stufen die
		// schnelligkeit konfigurieren kann (schnell, mittel, langsam)
		JSlider difficultySlider = new JSlider(1, 3, 2);
		difficultySlider.setMajorTickSpacing(1);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setPaintLabels(true);
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(1, new JLabel("Slow"));
		labelTable.put(2, new JLabel("Normal"));
		labelTable.put(3, new JLabel("Schnell"));
		difficultySlider.setLabelTable(labelTable);

		// Setze das Layout des Panels auf null, um absolute Positionierung zu verwenden
		this.setLayout(null);

		// Setze die Position und Größe des Sliders
		difficultySlider.setBounds(150, 350, 300, 50); // x, y, Breite, Höhe

		// Füge den Slider dem Panel hinzu
		this.add(difficultySlider);

		difficultySlider.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int difficulty = source.getValue();
				System.out.println("Schwierigkeit: " + difficulty);
				if (difficulty == 1)
				{
					difficulty = SLOW;
				}
				if (difficulty == 2)
				{
					difficulty = NORMAL;
				}
				if (difficulty == 3)
				{
					difficulty = FAST;
				}
				System.out.println("Schwierigkeit: " + difficulty);
				
				GameScreenPanel.setSnakeSpeed(difficulty);
			}
		});

		JButton button = new JButton("Play");
		this.setLayout(null);
		button.setBounds(150, 450, 300, 50);
		this.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Wechsel zum GameScreen
				cardLayout.show(mainPanel, "GameScreen");
//				SoundManager.stopLoop();
//				SoundManager.playLoop("/sound_backgroundMusic/nostale_theme_2.wav");
			}
		});
	}
}
