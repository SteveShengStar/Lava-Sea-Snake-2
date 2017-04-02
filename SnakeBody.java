import java.util.ArrayDeque;
import java.util.Iterator;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class SnakeBody extends SnakeMovement{
	private ArrayDeque<SnakeCell> snakeBody;
	private double width;	// Read-Only properties
	private Color bodyColor;
	private Iterator<SnakeCell> snakeBodyIt;
	
	public SnakeBody(int initialX, int initialY, int width, Color color, int crawlDistance){
		// Create a Snake instance with one square
		super (crawlDistance);
		snakeBody = new ArrayDeque<SnakeCell>();
		this.bodyColor = color;
		this.width = width;
		snakeBody.addFirst(new SnakeCell((double)initialX, (double)initialY, (double)width, bodyColor));
	}
	
	// Getters and Setters
	public int getSnakeWidth (){
		return (int)width;
	}
	public void setColor(Color color){
		bodyColor = color;
	}
	public ArrayDeque<SnakeCell> getSnakeBody(){
		return snakeBody;
	}
	
	// Animates the snake moving one tile forward. Handles all collision testing
	public Node animate(){
		SnakeCell newHead = new SnakeCell(snakeBody.getFirst().getX() + crawlX, snakeBody.getFirst().getY() + crawlY, width, bodyColor);
		Node collidingObject;	
		snakeBody.addFirst(newHead);
		
		// Check if the Snake is eating a fish through collision test
		collidingObject = collidingWith(newHead, MainClass.allFish.toArray(new Fish[0]));
		if (collidingObject != null) return collidingObject;

		snakeBody.removeLast();
		
		// End the game if snake goes out of bounds
		if (newHead.getX() < MainClass.grid.getLeftBorder() || newHead.getX() >= MainClass.grid.getRightBorder() || newHead.getY() < MainClass.grid.getTopBorder() || newHead.getY() >= MainClass.grid.getBottomBorder()){
			newHead.setFill(Color.RED); 
			return newHead;
		}
		
		// Check if the snake collided with the lava pools
		collidingObject = collidingWith(newHead, MainClass.lavaPools.getLavaPoolArray());
		if (collidingObject != null){
			newHead.setFill(Color.RED);
			return collidingObject;
		}
		
		snakeBodyIt = snakeBody.iterator();
		SnakeCell s;
		snakeBodyIt.next();
		// Check whether the snake ate its own tail
		while(snakeBodyIt.hasNext()){
			s = snakeBodyIt.next();
			if (newHead.getBoundsInLocal().intersects(s.getBoundsInLocal())){
				s.setFill(Color.RED);
				return newHead;
			}
		}
		return null;
	}
	// Method for detecting collisions
	public Node collidingWith(SnakeCell newHead, Node[] objects){
		for (Node someObject: objects){
			if (newHead.getBoundsInLocal().intersects(someObject.getBoundsInLocal())){
				if ((someObject instanceof Fish) && canReverse)
					canReverse = false;
				return someObject;
			}
		}
		return null;
	}
}
