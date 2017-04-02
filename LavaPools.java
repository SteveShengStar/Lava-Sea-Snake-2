import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class LavaPools {
	private Rectangle[] lavaPoolArray;
	private Grid parentGrid;
	
	public LavaPools(int scenario, Color poolColor, Grid parentGrid){
		this.parentGrid = parentGrid;
		switch(scenario){
			case 0:
				lavaPoolArray = new Rectangle[17];
				break;
			case 1:
				lavaPoolArray = new Rectangle[16];
				break;
			case 2:
				lavaPoolArray = new Rectangle[15];
		}
		for (int i = 0; i < lavaPoolArray.length; ++i){
			lavaPoolArray[i] = new Rectangle();
			lavaPoolArray[i].setFill(poolColor);
			lavaPoolArray[i].setStrokeWidth(parentGrid.getLineWidth());
			lavaPoolArray[i].setStroke(poolColor);
			lavaPoolArray[i].setOpacity(0.8);
		}
		switch(scenario){
			case 0:
				convertToWindowCoordinates(lavaPoolArray[0], 3, 0, 6, 2);
				convertToWindowCoordinates(lavaPoolArray[1], 7, 2, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[2], 6, 14, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[3], 3, 14, 1, 10);
				convertToWindowCoordinates(lavaPoolArray[4], 4, 19, 5, 1);
				convertToWindowCoordinates(lavaPoolArray[5], 4, 20, 4, 1);
				convertToWindowCoordinates(lavaPoolArray[6], 4, 21, 3, 1);
				convertToWindowCoordinates(lavaPoolArray[7], 3, 24, 6, 1);
				convertToWindowCoordinates(lavaPoolArray[8], 15, 12, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[9], 11, 24, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[10], 23, 7, 1, 3);
				convertToWindowCoordinates(lavaPoolArray[11], 24, 8, 1, 1);
				convertToWindowCoordinates(lavaPoolArray[12], 26, 7, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[13], 28, 6, 1, 4);
				convertToWindowCoordinates(lavaPoolArray[14], 20, 19, 2, 2);
				convertToWindowCoordinates(lavaPoolArray[15], 22, 19, 3, 3);
				convertToWindowCoordinates(lavaPoolArray[16], 25, 19, 1, 1);
				break;
			case 1:
				convertToWindowCoordinates(lavaPoolArray[0], 0, 2, 5, 1);
				convertToWindowCoordinates(lavaPoolArray[1], 2, 3, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[2], 4, 5, 1, 4);
				convertToWindowCoordinates(lavaPoolArray[3], 5, 8, 1, 2);
				convertToWindowCoordinates(lavaPoolArray[4], 6, 9, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[5], 8, 9, 1, 3);
				convertToWindowCoordinates(lavaPoolArray[6], 1, 12, 1, 2);
				convertToWindowCoordinates(lavaPoolArray[7], 4, 20, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[8], 8, 20, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[9], 19, 4, 2, 3);
				convertToWindowCoordinates(lavaPoolArray[10], 21, 6, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[11], 23, 5, 1, 1);
				convertToWindowCoordinates(lavaPoolArray[12], 14, 24, 5, 1);
				convertToWindowCoordinates(lavaPoolArray[13], 27, 16, 2, 2);
				convertToWindowCoordinates(lavaPoolArray[14], 25, 18, 4, 2);
				convertToWindowCoordinates(lavaPoolArray[15], 9, 21, 1, 2);
				break;
			case 2:
				convertToWindowCoordinates(lavaPoolArray[0], 0, 0, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[1], 3, 0, 3, 1);
				convertToWindowCoordinates(lavaPoolArray[2], 1, 6, 1, 3);
				convertToWindowCoordinates(lavaPoolArray[3], 3, 6, 1, 2);
				convertToWindowCoordinates(lavaPoolArray[4], 4, 6, 1, 3);
				convertToWindowCoordinates(lavaPoolArray[5], 2, 21, 1, 1);
				convertToWindowCoordinates(lavaPoolArray[6], 2, 20, 5, 1);
				convertToWindowCoordinates(lavaPoolArray[7], 5, 21, 2, 1);
				convertToWindowCoordinates(lavaPoolArray[8], 5, 22, 3, 1);
				convertToWindowCoordinates(lavaPoolArray[9], 10, 8, 1, 2);
				convertToWindowCoordinates(lavaPoolArray[10], 14, 9, 1, 1);
				convertToWindowCoordinates(lavaPoolArray[11], 14, 10, 3, 2);
				convertToWindowCoordinates(lavaPoolArray[12], 11, 12, 6, 4);
				convertToWindowCoordinates(lavaPoolArray[13], 24, 5, 3, 1);
				convertToWindowCoordinates(lavaPoolArray[14], 27, 1, 3, 2);
		}
	}
	
	private void convertToWindowCoordinates(Rectangle rectangle, int x, int y, int width, int height){
		rectangle.setX(parentGrid.getXWindowCoordinates(x));
		rectangle.setY(parentGrid.getYWindowCoordinates(y));
		rectangle.setWidth(parentGrid.getWindowDimension(width));
		rectangle.setHeight(parentGrid.getWindowDimension(height));
	}
	public Rectangle[] getLavaPoolArray(){
		return lavaPoolArray;
	}
}
