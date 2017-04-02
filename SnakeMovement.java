import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
 
public class SnakeMovement{
	enum Direction {STATIONARY, UP, DOWN, LEFT, RIGHT};
	protected int crawlX, crawlY, crawlDistance;	// The distance the snake covers per frame
	protected Direction playerDirection;
	protected boolean canReverse; // Indicates whether the snake can reverse direction
	
	public Direction getDirection(){
		return playerDirection;
	}
	
	public void initialize(){
		playerDirection = Direction.STATIONARY;
		crawlX = 0;
		crawlY = 0;
		canReverse = true;
	}
	
	public SnakeMovement(int crawlDistance){
		this.crawlDistance = crawlDistance;
		initialize();
	}

	// Handler for key inputs and changing the Snake's direction
	public class KeyPressedHandler implements EventHandler<KeyEvent>{
		public void handle(KeyEvent e){
			switch(e.getCode()){
				case A: 
					if (canReverse || playerDirection != Direction.RIGHT){
						playerDirection = Direction.LEFT;
						crawlX = -crawlDistance;
						crawlY = 0;
					}
					break;
				case D:
					if (canReverse || playerDirection != Direction.LEFT){
						playerDirection = Direction.RIGHT;
						crawlX = crawlDistance;
						crawlY = 0;
					}
					break;
				case S:
					if (canReverse || playerDirection != Direction.UP){
						playerDirection = Direction.DOWN;
						crawlX = 0;
						crawlY = crawlDistance;
					}
					break;
				case W:
					if (canReverse || playerDirection != Direction.DOWN){
						playerDirection = Direction.UP;
						crawlX = 0;
						crawlY = -crawlDistance;
					}
			}
		}
	}
}
