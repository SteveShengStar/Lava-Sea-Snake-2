import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Grid{
	private Color color;
	private int tileSeparation, startX, startY, numRows, numCols, lineWidth, endX, endY;
	private ArrayList<Line> hLines, vLines;
	
	// Constructors
	public Grid(Color color){
		this(color, 22);
	}

	public Grid(Color color, int tileSeparation){
		this.color = color;
		this.tileSeparation = tileSeparation;
		this.startX = 35;
		this.startY = 35;
		this.numRows = 25;
		this.numCols = 30;
		this.lineWidth = 2;
		hLines = new ArrayList<Line>(numRows + 1);
		vLines = new ArrayList<Line>(numCols + 1);
		//hLines = new Line[numCols + 1];
		//vLines = new Line[numRows + 1];
		endX = startX + (lineWidth + tileSeparation) * numCols;
		endY = startY + (lineWidth + tileSeparation) * numRows;
	}
	
	// Set the properties (positions, lengths) of the lines on the grid
	public void initializeLines(){
		// Initialize horizontal lines' properties
		for (int i = 0; i <= numRows; ++i){
			hLines.add(new Line((double)(startX - 1), (double)(startY + (lineWidth + tileSeparation)*i -1), (double)(endX - 1), (double)(startY + (lineWidth + tileSeparation)*i - 1)));
			hLines.get(i).setStrokeWidth(lineWidth);
			hLines.get(i).setStroke(color);
		}
		// Initialize vertical lines' properties
		for (int i = 0; i <= numCols; ++i){
			vLines.add(new Line((double)(startX + (lineWidth + tileSeparation)*i - 1), (double)(startY - 1), (double)(startX + (lineWidth + tileSeparation)*i -1), (double)(endY - 1)));
			vLines.get(i).setStrokeWidth(lineWidth);
			vLines.get(i).setStroke(color);
		}
	}
	
	// Getters and Setters
	public void setColor(Color color){
		this.color = color;
	}
	
	// Getters for Read-Only Properties
	public int getSeparation(){
		return tileSeparation;
	}
	public int getLineWidth(){
		return lineWidth;
	}
	public int getLeftBorder(){
		return startX;
	}
	public int getRightBorder(){
		return endX;
	}
	public int getTopBorder(){
		return startY;
	}
	public int getBottomBorder(){
		return endY;
	}
	public int getNumRows(){
		return numRows;
	}
	public int getNumColumns(){
		return numCols;
	}
	public ArrayList<Line> getHLines(){
		return hLines;
	}
	public ArrayList<Line> getVLines(){
		return vLines;
	}
	// Converts a coordinate on the Grid to a coordinate on the display Window
	public int getXWindowCoordinates(int x){
		return (int)vLines.get(x).getStartX() + 1;
	}
	public int getYWindowCoordinates(int y){
		return (int)hLines.get(y).getStartY() + 1;
	}
	public int getWindowDimension(int unitLength){
		return (tileSeparation + lineWidth) * unitLength - lineWidth;
	}
}
