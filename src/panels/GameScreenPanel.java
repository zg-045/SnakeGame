package panels;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import sonstiges.SoundManager;
import spiel_ojekte.Apple;
import spiel_ojekte.Snake;

/**
 * Diese Klasse ist zuständig für die den Panel und die Aktualisierung der
 * Grafiken
 */
public class GameScreenPanel extends JPanel implements ActionListener {

	private Apple apple;
	private Snake snake;
	public static int score;
	public static int highscore;
	private static Timer timer;
	private boolean gameOver;

	private JPanel mainPanel;
	private CardLayout cardLayout;

	private Image background;

	private Image appleImage;

	private Image snakeHeadUpImage;
	private Image snakeHeadDownImage;
	private Image snakeHeadRightImage;
	private Image snakeHeadLeftImage;

	private Image tailDown;
	private Image tailLeft;
	private Image tailRight;
	private Image tailUp;

	private Image bodyBottomLeft;
	private Image bodyBottomRight;
	private Image bodyHorizontal;
	private Image bodyTopLeft;
	private Image bodyTopRight;
	private Image bodyVertical;

	/**
	 * Der Konstruktor der Klasse
	 */
	public GameScreenPanel(CardLayout cardLayout, JPanel mainPanel) {

		score = 0;
		highscore = 0;

		gameOver = false;

		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;

		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		//Geschwindigkeit zunächst irrelevant, da es am Slider verändert wird.
		timer = new Timer(50, this);

		appleImage = new ImageIcon(getClass().getResource("/images_apple/apple.png")).getImage();

		snakeHeadUpImage = new ImageIcon(getClass().getResource("/images_snake_head/head_up.png")).getImage();
		snakeHeadDownImage = new ImageIcon(getClass().getResource("/images_snake_head/head_down.png")).getImage();
		snakeHeadRightImage = new ImageIcon(getClass().getResource("/images_snake_head/head_right.png")).getImage();
		snakeHeadLeftImage = new ImageIcon(getClass().getResource("/images_snake_head/head_left.png")).getImage();

		tailDown = new ImageIcon(getClass().getResource("/images_snake_tail/tail_down.png")).getImage();
		tailLeft = new ImageIcon(getClass().getResource("/images_snake_tail/tail_left.png")).getImage();
		tailRight = new ImageIcon(getClass().getResource("/images_snake_tail/tail_right.png")).getImage();
		tailUp = new ImageIcon(getClass().getResource("/images_snake_tail/tail_up.png")).getImage();

		bodyBottomLeft = new ImageIcon(getClass().getResource("/images_snake_body/body_bottomleft.png")).getImage();
		bodyBottomRight = new ImageIcon(getClass().getResource("/images_snake_body/body_bottomright.png")).getImage();
		bodyHorizontal = new ImageIcon(getClass().getResource("/images_snake_body/body_horizontal.png")).getImage();
		bodyTopLeft = new ImageIcon(getClass().getResource("/images_snake_body/body_topleft.png")).getImage();
		bodyTopRight = new ImageIcon(getClass().getResource("/images_snake_body/body_topright.png")).getImage();
		bodyVertical = new ImageIcon(getClass().getResource("/images_snake_body/body_vertical.png")).getImage();

		background = new ImageIcon(getClass().getResource("/images_background/SnakeHintergrund.jpg")).getImage();
		
		
		this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                // Fordere den Fokus an, wenn das GamePanel sichtbar wird
                requestFocusInWindow();
//    			SoundManager.playLoop("/sound_backgroundMusic/nostale_theme_2.wav");
            }
        });

	}
	/**
	 * Konfiguriert die Geschwindigkeit der Schlange
	 * @param speed
	 */
	public static void setSnakeSpeed(int speed) {
		if (speed == 50) {
			timer.setDelay(50);
		}
		
		if (speed == 150) {
			timer.setDelay(150);
		}
		
		if (speed == 25) {
			timer.setDelay(25);
		}
	}

	/**
	 * Die Methode zeichnet das Spielfeld sowohl die Äpfel, der Snake, Scoreboard
	 * und Deathboard.
	 * 
	 * Sie ruft intern draw() auf
	 * 
	 * Diese Methode wird automatisch aufgerufen, wenn ein Panel erzeugt wird.
	 * Ebenfalls wird diese Methode durch repaint() aufgerufen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawSnake();
		drawApple();
		drawGameScreen(g);

		if (gameOver) {


			g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
			drawSnakeBody(g);
			g.drawImage(snakeHeadRightImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
					snake.getUnitSize(), this);

//			SoundManager.stopLoop();
			timer.stop();

		}

	}
	
	/**
	 * Erzeugt den initialen Apfel im Spielfeld
	 */
	public void drawApple() {
		
		if (apple == null)
		{
			apple = new Apple(getWidth(), getHeight());
		}
	}
	/**
	 * Erzeugt die initiale Schlange im Spielfeld
	 */
	public void drawSnake() {
		if (snake == null)
		{
			snake = new Snake(getWidth(), getHeight());
		}
	}

	/**
	 * Diese Methode zeichnet den Hintergrund, die Schlange, die Äpfel sowie den
	 * Scoreboard
	 * 
	 * @param g
	 */
	public void drawGameScreen(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(appleImage, apple.getXPosition(), apple.getYPosition(), apple.getAppleSize(), apple.getAppleSize(),
				this);

		drawSnakeHead(g);
		drawSnakeBody(g);
		drawScoreBoard(g);
	}

	/**
	 * Zeichnet den Scoreboard
	 * 
	 * @param g
	 */
	private void drawScoreBoard(Graphics g) {
		// Punktestand anzeigen
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Score: " + score, 10, 20);

	}

	/**
	 * Zeichnet den Körper der Schlange (und nicht den Kopf)
	 * 
	 * @param g
	 */
	private void drawSnakeBody(Graphics g) {
		if (snake.getBodySize() >= 3) {
			for (int j = 1; j < snake.getBodySize() - 1; j++) {
				// Vertikal
				// y-achse gleich mit vorgänger, y-achse verschieden mit vorgänger
				if (snake.getBody().get(j)[0] == snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] != snake.getBody().get(j - 1)[1]
						// x-achse gleich mit nachfolger, y-achse gleich mit nachfolger
						&& snake.getBody().get(j)[0] == snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] != snake.getBody().get(j + 1)[1]) {

					g.drawImage(bodyVertical, snake.getBody().get(j)[0], snake.getBody().get(j)[1], snake.getUnitSize(),
							snake.getUnitSize(), this);

				}
				// Horizontal
				if (snake.getBody().get(j)[0] != snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j - 1)[1]
						// x-achse verschieden mit nachfolger, y-achse gleich mit nachfolger
						&& snake.getBody().get(j)[0] != snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j + 1)[1]) {

					g.drawImage(bodyHorizontal, snake.getBody().get(j)[0], snake.getBody().get(j)[1],
							snake.getUnitSize(), snake.getUnitSize(), this);

				}

				// BottomLeft
				if (snake.getBody().get(j)[0] == snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] < snake.getBody().get(j - 1)[1]
						// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
						&& snake.getBody().get(j)[0] > snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j + 1)[1]
						//////////////////////////
						|| (snake.getBody().get(j)[0] > snake.getBody().get(j - 1)[0]
								&& snake.getBody().get(j)[1] == snake.getBody().get(j - 1)[1]
								// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
								&& snake.getBody().get(j)[0] == snake.getBody().get(j + 1)[0]
								&& snake.getBody().get(j)[1] < snake.getBody().get(j + 1)[1])) {

					g.drawImage(bodyBottomLeft, snake.getBody().get(j)[0], snake.getBody().get(j)[1],
							snake.getUnitSize(), snake.getUnitSize(), this);

				}
				// Bottomright
				if (snake.getBody().get(j)[0] < snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j - 1)[1]
						// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
						&& snake.getBody().get(j)[0] == snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] < snake.getBody().get(j + 1)[1]
						//////////////////////////
						|| (snake.getBody().get(j)[0] == snake.getBody().get(j - 1)[0]
								&& snake.getBody().get(j)[1] < snake.getBody().get(j - 1)[1]
								// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
								&& snake.getBody().get(j)[0] < snake.getBody().get(j + 1)[0]
								&& snake.getBody().get(j)[1] == snake.getBody().get(j + 1)[1])) {

					g.drawImage(bodyBottomRight, snake.getBody().get(j)[0], snake.getBody().get(j)[1],
							snake.getUnitSize(), snake.getUnitSize(), this);

				}

				// TopLeft
				if (snake.getBody().get(j)[0] == snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] > snake.getBody().get(j - 1)[1]
						// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
						&& snake.getBody().get(j)[0] > snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j + 1)[1]
						//////////////////////////
						|| (snake.getBody().get(j)[0] > snake.getBody().get(j - 1)[0]
								&& snake.getBody().get(j)[1] == snake.getBody().get(j - 1)[1]
								// x-achse gleich mit nachfolger, y-achse verschieden mit nachfolger
								&& snake.getBody().get(j)[0] == snake.getBody().get(j + 1)[0]
								&& snake.getBody().get(j)[1] > snake.getBody().get(j + 1)[1])) {

					g.drawImage(bodyTopLeft, snake.getBody().get(j)[0], snake.getBody().get(j)[1], snake.getUnitSize(),
							snake.getUnitSize(), this);

				}

				// TopRight
				if (snake.getBody().get(j)[0] == snake.getBody().get(j - 1)[0]
						&& snake.getBody().get(j)[1] > snake.getBody().get(j - 1)[1]
						&& snake.getBody().get(j)[0] < snake.getBody().get(j + 1)[0]
						&& snake.getBody().get(j)[1] == snake.getBody().get(j + 1)[1]
						//////////////////////////
						|| (snake.getBody().get(j)[0] < snake.getBody().get(j - 1)[0]
								&& snake.getBody().get(j)[1] == snake.getBody().get(j - 1)[1]
								&& snake.getBody().get(j)[0] == snake.getBody().get(j + 1)[0]
								&& snake.getBody().get(j)[1] > snake.getBody().get(j + 1)[1])) {

					g.drawImage(bodyTopRight, snake.getBody().get(j)[0], snake.getBody().get(j)[1], snake.getUnitSize(),
							snake.getUnitSize(), this);

				}
			}

			// TailLeft

			if (snake.getBody().get(snake.getBodySize() - 1)[0] < snake.getBody().get(snake.getBodySize() - 2)[0]) {
				g.drawImage(tailLeft, snake.getBody().get(snake.getBodySize() - 1)[0],
						snake.getBody().get(snake.getBodySize() - 1)[1], snake.getUnitSize(), snake.getUnitSize(),
						this);
			}

			// TailRight

			if (snake.getBody().get(snake.getBodySize() - 1)[0] > snake.getBody().get(snake.getBodySize() - 2)[0]) {
				g.drawImage(tailRight, snake.getBody().get(snake.getBodySize() - 1)[0],
						snake.getBody().get(snake.getBodySize() - 1)[1], snake.getUnitSize(), snake.getUnitSize(),
						this);
			}

			// TailUp

			if (snake.getBody().get(snake.getBodySize() - 1)[1] < snake.getBody().get(snake.getBodySize() - 2)[1]) {
				g.drawImage(tailUp, snake.getBody().get(snake.getBodySize() - 1)[0],
						snake.getBody().get(snake.getBodySize() - 1)[1], snake.getUnitSize(), snake.getUnitSize(),
						this);
			}

			// TailDown

			if (snake.getBody().get(snake.getBodySize() - 1)[1] > snake.getBody().get(snake.getBodySize() - 2)[1]) {
				g.drawImage(tailDown, snake.getBody().get(snake.getBodySize() - 1)[0],
						snake.getBody().get(snake.getBodySize() - 1)[1], snake.getUnitSize(), snake.getUnitSize(),
						this);
			}
		}
	}

	/**
	 * Zeichnet den Kopf der Schlange (und nicht den Körper)
	 * 
	 * @param g
	 */
	private void drawSnakeHead(Graphics g) {
		// Zeichnet anfangs den Kopf in der Startposition
		g.drawImage(snakeHeadRightImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
				snake.getUnitSize(), this);

		if (snake.getDirection() == 'D') {
			g.drawImage(snakeHeadDownImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
					snake.getUnitSize(), this);
		}
		if (snake.getDirection() == 'U') {
			g.drawImage(snakeHeadUpImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
					snake.getUnitSize(), this);
		}
		if (snake.getDirection() == 'R') {
			g.drawImage(snakeHeadRightImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
					snake.getUnitSize(), this);
		}
		if (snake.getDirection() == 'L') {
			g.drawImage(snakeHeadLeftImage, snake.getSnakeHead()[0], snake.getSnakeHead()[1], snake.getUnitSize(),
					snake.getUnitSize(), this);
		}
	}

	/**
	 * Gibt das Apple-Objekt zurück
	 * 
	 * @return
	 */
	public Apple getApple() {
		return apple;
	}

	/**
	 * Gibt das Snake-Objekt zurück
	 * 
	 * @return
	 */
	public Snake getSnake() {
		return snake;
	}

	/**
	 * Wird durch den Timer-Objekt aufgerufen in einem festen Intervall.
	 * 
	 * Zeichnet die Schlange nach einer Bewegung neu, prüft, ob die Schlange gegen
	 * eine Wand, gegen sich selbst oder einem Apfel kollidiert. Aktualisiert
	 * entsprechend das Scoreboard und spielt einen Sound ab, wenn die Schlange
	 * einen Apfel frisst.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
			// Bewege die Schlange bei jedem Timer-Intervall
			snake.move();

			if (snake.checkAppleCollision(apple)) {
				snake.grow();
				apple.newApple();
				score += 10;

				SoundManager.playSound("/sound_soundeffect/apple_crunch.wav");
			}
			
			if (snake.checkCollisionAgainstWall()) {
				timer.stop();
				System.out.println("Kollision mit der Wand erkannt.");
				gameOver = true;
				SoundManager.stopLoop();
//				SoundManager.playLoop("/nostale_theme_3.wav");
//				timer.stop();
				cardLayout.show(mainPanel, "DeathScreen");
				
				
			}

			if (snake.collidesAgainstItself()) {
				timer.stop();
				System.out.println("Selbstkollision erkannt.");
				gameOver = true;
				//stoppt nicht den Loop, warum auch immer nicht
//				SoundManager.stopLoop();
//				SoundManager.playLoop("/nostale_theme_3.wav");
//				timer.stop();
				cardLayout.show(mainPanel, "DeathScreen");
//				timer.stop();
			}

			repaint();
		}
		else
		{
//			SoundManager.stopLoop();
			timer.stop();
//			SoundManager.playLoop("/nostale_theme_3.wav");
		}
	}

	
	
	public void resetGame() {
	    snake = new Snake(getWidth(), getHeight()); 
	    apple = new Apple(getWidth(), getHeight()); 
	    score = 0;   
	    gameOver = false;  
//	    timer.start();     
	    repaint();         
	}


	/**
	 * Reagiert auf den Tastenschlag des Spielers und setzt die Richtung des Kopfes
	 * in die jeweilige Richtung der Taste
	 */
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (!gameOver) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					snake.setDirection('L');
					break;
				case KeyEvent.VK_RIGHT:
					snake.setDirection('R');
					break;
				case KeyEvent.VK_UP:
					snake.setDirection('U');
					break;
				case KeyEvent.VK_DOWN:
					snake.setDirection('D');
					break;
				}
				repaint();
				timer.start();
			}

		}

	}
}