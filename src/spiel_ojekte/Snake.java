package spiel_ojekte;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	
	private int size; 
	//der Körper der Schlange besteht aus eine Liste von Arrays, wobei jedes Array, die x und y Koordinate
	//des jeweiligen Blocks speichert.
	private List<int[]> body; 
	private char direction;	
	private int panelWidth;
	private int panelHeight;
	
	public Snake(int panelWidth, int panelHeight) {
		
		size = 20;
		direction = 'r';		
		// die Initiale x und y Position des Kopfes, beim Start des Spiels
		body = new ArrayList<>();
		//fügt den Kopf der Schlange ein
		body.add(new int[] {size*5, size*5});
		body.add(new int[] {size*4, size*5});
		body.add(new int[] {100-2*size, 100});
		
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		
		
		
	}
	
	/**
	 * Die Methode implementiert die interne Bewegung der Schlange
	 */
	public void move() {
	    // Speichere die alte Kopfposition, bevor sie aktualisiert wird
	    int oldHeadX = getSnakeHead()[0];
	    int oldHeadY = getSnakeHead()[1];

	    // Bewegung des Rumpfes
	    for (int i = body.size() - 1; i > 0; i--) {
	        body.get(i)[0] = body.get(i - 1)[0]; // x-Position
	        body.get(i)[1] = body.get(i - 1)[1]; // y-Position
	    }

	    // Aktualisiere die Kopfposition
	    int newX = oldHeadX;
	    int newY = oldHeadY;

	    switch (direction) {
	        case 'L':  // Links
	            newX -= size;
	            break;
	        case 'R':  // Rechts
	            newX += size;
	            break;
	        case 'U':  // Hoch
	            newY -= size;
	            break;
	        case 'D':  // Runter
	            newY += size;
	            break;
	    }

	    // Setze die neue Position des Kopfes
	    body.get(0)[0] = newX;
	    body.get(0)[1] = newY;
	}
	
	/**
	 * Sorgt dafür, dass die Schlange um eine Einheit wächst
	 */
	public void grow() {
	    int[] lastBodyPart = body.get(getBodySize() - 1);  // Letztes Segment der Schlange
	    int[] newBodyPart = new int[2];

	    // Abhängig von der Bewegungsrichtung der Schlange das neue Segment platzieren
	    switch (direction) {
	        case 'L':  // Nach links bewegen
	            newBodyPart[0] = lastBodyPart[0] + size;
	            newBodyPart[1] = lastBodyPart[1];
	            break;
	        case 'R':  // Nach rechts bewegen
	            newBodyPart[0] = lastBodyPart[0] - size;
	            newBodyPart[1] = lastBodyPart[1];
	            break;
	        case 'U':  // Nach oben bewegen
	            newBodyPart[0] = lastBodyPart[0];
	            newBodyPart[1] = lastBodyPart[1] + size;
	            break;
	        case 'D':  // Nach unten bewegen
	            newBodyPart[0] = lastBodyPart[0];
	            newBodyPart[1] = lastBodyPart[1] - size;
	            break;
	    }

	    // Füge das neue Segment zur Schlange hinzu
	    body.add(newBodyPart);
	}
	
	/**
	 * Prüft ob der Kopf der Schlange den Apfel berührt
	 * @param apple
	 * @return
	 */
	public boolean checkAppleCollision(Apple apple) {
	    // Hole die Position des Kopfes der Schlange
	    int[] snakeHead = getSnakeHead();
	    
	    int snakeX = snakeHead[0];
	    int snakeY = snakeHead[1];
	    int appleX = apple.getXPosition();
	    int appleY = apple.getYPosition();
	    
	    
	    // Überprüfe, ob sich die Bereiche von Apfel und Schlangenkopf überschneiden
	    boolean collisionX = snakeX == appleX && snakeX + size > appleX;
	    boolean collisionY = snakeY < appleY + size && snakeY + size > appleY;
	    
	    // Wenn beide Bedingungen wahr sind, liegt eine Kollision vor
	    return collisionX && collisionY;
	}
	
		/**
		 * Prüft ob die Schlange gegen eine Wand läuft
		 * 
		 * @return true, falls es gegen eine Wand läuft
		 */
		public boolean checkCollisionAgainstWall() {
			int[] snakeHead = getSnakeHead();
			int snakeHeadX = snakeHead[0];
			int snakeHeadY = snakeHead[1];

			if (snakeHeadX < 0 || snakeHeadX > panelWidth || snakeHeadY < 0 || snakeHeadY > panelHeight) {
				return true;
			}
			return false;
		}
		
	
		/**
		 * Prüft, ob der Kopf der Schlange gegen den Körper der Schlange kollidiert.
		 * 
		 * @return
		 */
		public boolean collidesAgainstItself() {
			int[] snakeHead = getSnakeHead();
			int snakeHeadX = snakeHead[0];
			int snakeHeadY = snakeHead[1];

			for (int i = 1; i < getBodySize(); i++) {
				int[] segment = getBody().get(i);
				if (snakeHeadX == segment[0] && snakeHeadY == segment[1]) {
					return true;
				}
			}
			return false;
		}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(char direction) {
		
		//verhindert, dass die Schlange in sich herein gehen kann
		if (this.direction == 'U' && direction == 'D' ||
			this.direction == 'D' && direction == 'U' ||
			this.direction == 'L' && direction == 'R' ||
			this.direction == 'R' && direction == 'L')
		{
			//mache nichts
		}
		else
		{
			this.direction = direction;
		}
		
	}
	
	
	
	public int getBodySize() {
		return body.size();
	}
	
	
	public int getUnitSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<int[]> getBody(){
		return body;
	}
	
	public int[] getSnakeHead() {
		return body.get(0);
	}
	
	public int[] getSnakeTail() {
		return body.get(body.size()-1);
		
	}
	
	
	
}
