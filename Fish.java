import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Iterator;
import java.util.Random;

public class Fish extends ImageView{
	protected Grid parentGrid;
	protected static Random random = new Random();
	
	// The arrays below define the properties of every type of Fish object
	// Sub-classing the different Fish classes was not feasible and led to lagging issues, so defining arrays was an effective work-around
	protected static Image[] images = {new Image("Images/Fish0.png"), new Image("Images/Fish1.png"), new Image("Images/Fish2.png"), new Image("Images/Fish3.png"), new Image("Images/Fish4.png"), new Image("Images/Fish5.png")};
	protected static int[] pointValues = {60, 30, 25, 85, 12, 301};
	protected static String[] fishType = {"Rainbow Fish", "Sea Horse", "Trout", "Jellyfish", "Gold Fish", "Treasure Fish"};
	
	protected int pointValue;
	protected static final int randomBound = 40;	// upper bound for the static random number generator
	
	public Fish(Grid parentGrid, int width, int height){
		this.parentGrid = parentGrid;
		
		setNewFishType();
		
		reposition();
		setFitWidth((double)width);
		setFitHeight((double)height);
	}
	
	// Getters and Setters
	private void setX(int xOnGrid){
		super.setX(parentGrid.getXWindowCoordinates(xOnGrid));
	}
	private void setY(int yOnGrid){
		super.setY(parentGrid.getYWindowCoordinates(yOnGrid));
	}
	public int getPointValue(){
		return pointValue;
	}

	public void reposition(){
		do{
			this.setX(Fish.random.nextInt(parentGrid.getNumColumns()));
			this.setY(Fish.random.nextInt(parentGrid.getNumRows()));
		}while(willCollide());
	}
	
	// Method for collision detection
	public boolean willCollide(){
		int obstacleArrayLength;
		
		// Check for a collision with the snake body
		Iterator<SnakeCell> snakeBodyIt = MainClass.player1.getSnakeBody().iterator(); 
		while (snakeBodyIt.hasNext()){
			SnakeCell s = snakeBodyIt.next();
			
			if (getBoundsInLocal().intersects(s.getBoundsInLocal()))
				return true;
		}
		
		// Check for a collision with lava pools
		obstacleArrayLength = MainClass.lavaPools.getLavaPoolArray().length;
		for (int i = 0; i < obstacleArrayLength; i++){
			if (getBoundsInLocal().intersects(MainClass.lavaPools.getLavaPoolArray()[i].getBoundsInLocal()))
				return true;
		}
		
		// Check for a collision with other Fish objects
		obstacleArrayLength = MainClass.allFish.size();
		for (int i = 0; i < obstacleArrayLength; ++i){
			if (MainClass.allFish.get(i) == this)
				continue;
			if (getBoundsInLocal().intersects(MainClass.allFish.get(i).getBoundsInLocal()))
				return true;
		}
		return false;
	}
	
	// Once a fish is consumed by Snake, use random number generator to determine its new appearance.
	// Every fish type has a different appearance and point value associated with it
	public void setNewFishType(){
		int tempInt = Fish.random.nextInt(randomBound);
		int tempIndex = 5;
		
		if (tempInt < 15)
			tempIndex = 4;
		else if (tempInt < 25)
			tempIndex = 2;
		else if (tempInt < 32)
			tempIndex = 1;
		else if (tempInt < 36)
			tempIndex = 0;
		else if (tempInt < 38)
			tempIndex = 3;
		
		pointValue = Fish.pointValues[tempIndex];
		setImage(Fish.images[tempIndex]);
	}
	// Alternative implementation of the above function
	public void setNewFishType(String type){
		for (int i = 0; i < fishType.length; ++i){
			if (type.equals(fishType[i])){
				pointValue = pointValues[i];
				setImage(Fish.images[i]);
				break;
			}
		}
	}
}
