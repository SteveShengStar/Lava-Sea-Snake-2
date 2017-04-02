import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class SnakeCell extends Rectangle{
	// Set the properties for every rectangle that makes up the snake. 
	public SnakeCell(double x, double y, double sideLength, Color color){
		super(x, y, sideLength, sideLength);
		super.setFill(color);
		super.setStrokeWidth(0);
	}
}
