package spiel_ojekte;
import java.util.Random;
/**
 * Diese Klasse modelliert die Apfel-Objekte im Spiel.
 */
public class Apple {
	
	private int xPosition;
	private int yPosition;
	private int size;
	//Paneldimension = 584x561
	
	private int panelWidth;
	private int panelHeight;
	
	/**
	 * Konstruktor
	 */
	public Apple(int panelWidth, int panelHeight) {
		
		Random random = new Random();
		size = 20;
		
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		System.out.println(panelWidth);
		
//		xPosition = random.nextInt((584-size)/size)*size;
//		yPosition = random.nextInt((561-size)/size)*size;
		xPosition = random.nextInt((panelWidth-size)/size)*size;
		yPosition = random.nextInt((panelHeight-size)/size)*size;
		
		
	}
	
	/**
	 * Entfernt den Apfel, das gefressen wurde, und fügt ein neuen Apfel
	 * an einer neuen Position ein.
	 */
	public void newApple() {
		Random random = new Random();
		xPosition = random.nextInt((584-size)/size)*size;
		yPosition = random.nextInt((561-size)/size)*size;
	}
	
	
	/**
	 * Gibt die x-Koordinate des Apfels zurück
	 * @return xPosition
	 */
	public int getXPosition() {
		return xPosition;
	}

	/**
	 * Setzt die x-Koordinate des Apfels
	 * @param xPosition
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * Gibt die x-Koordinate des Apfels zurück
	 * @return xPosition
	 */
	public int getYPosition() {
		return yPosition;
	}

	/**
	 * Setzt die x-Koordinate des Apfels
	 * @param xPosition
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	/**
	 * Gibt die Größe des Apfels zurücl
	 * @return size
	 */
	public int getAppleSize() {
		return size;
	}

	
	

	
	
	

}
