import javafx.application.Application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class MainClass extends Application{
	protected static Pane actionPanel;
	protected static VBox buttonPanel;
	protected static BorderPane mainPanel;
	protected Button btPlayPaused, btQuit, btReset;
	protected Label scoreLabel, gameOverLabel, gameOverScoreTitle, gameOverScoreLabel;
	
	public static SnakeBody player1;
	protected static Timeline animateSnake; 
	public static Grid grid;
	public static ArrayList<Fish> allFish;
	public final int numOfFish = 5;
	public static LavaPools lavaPools;
	
	protected static Random random = new Random();
	protected static long score;
	
	// Find out a Component's center coordinates given its top-left coordinates
	public static double convertToOrigin(int startCoord, int width){
		return (double)(startCoord * 2 + width) / 2.0;
	}
	// Main entry point. Initialize all components, set the scene, and render all graphics
	public void start(Stage primaryStage){	
		// Set up the Action Panel, where the gameplay takes place
		actionPanel = new Pane();
		
		// Create a Grid
		grid = new Grid(Color.WHITE);
		grid.initializeLines();
		actionPanel.getChildren().addAll(grid.getHLines());
		actionPanel.getChildren().addAll(grid.getVLines());
		
		// Creating lava pool obstacles
		lavaPools = new LavaPools(random.nextInt(3), Color.ORANGE, grid);
		for (Rectangle lavaPool: lavaPools.getLavaPoolArray()){
			actionPanel.getChildren().add(lavaPool);
		}
		
		// Instantiate a Snake
		player1 = new SnakeBody(grid.getXWindowCoordinates(5), grid.getYWindowCoordinates(5), grid.getSeparation(), Color.BLACK, grid.getSeparation() + grid.getLineWidth());
		actionPanel.getChildren().add(player1.getSnakeBody().getFirst());
		
		// Defining the Snake's animation properties
	    animateSnake = new Timeline(new KeyFrame(Duration.millis(225), new SnakeAnimateHandler()));
	    animateSnake.setCycleCount(Timeline.INDEFINITE);
	    animateSnake.play(); // Start animation
	    
		// Create Fish Objects
		allFish = new ArrayList<Fish>();
		for (int i = 0; i < numOfFish; ++i){
			allFish.add(new Fish(grid, grid.getSeparation(), grid.getSeparation()));
		}
		actionPanel.getChildren().addAll(allFish);
		
		// Register a key event handler with Action Panel
		actionPanel.setOnKeyPressed(player1.new KeyPressedHandler());
		actionPanel.setStyle("-fx-background-color: royalblue; -fx-opacity: 0.9;");
		
		// Set up the buttons/score display panel. Initialize all components within it
		buttonPanel = new VBox(27);
    	buttonPanel.setAlignment(Pos.CENTER);
    	
    	buttonPanel.setPadding(new Insets(45, 15, 15, 45));
    	btPlayPaused = new Button("Pause / Resume");
    	btQuit = new Button("Quit");
    	btReset = new Button("Reset");
    	btReset.setVisible(false);
    	
    	btPlayPaused.setOnAction(new PlayPausedHandler());
    	btQuit.setOnAction(new QuitHandler());
    	btReset.setOnAction(new ResetHandler());
    	
    	btPlayPaused.setMinWidth(180);
    	btQuit.setMinWidth(180);
    	btReset.setMinWidth(180);
    	
    	btPlayPaused.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 18));
    	btQuit.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 18));
    	btReset.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 18));
    	
    	buttonPanel.getChildren().addAll(btPlayPaused, btQuit, btReset);
    	
    	Label scoreTitle = new Label("Score ");
    	scoreTitle.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 30));
    	scoreTitle.setTextFill(Color.BLUE);
    	buttonPanel.getChildren().add(scoreTitle);
    	
    	scoreLabel = new Label(Long.toString(score));
    	scoreLabel.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 30));
    	scoreLabel.setTextFill(Color.BLUE);
    	buttonPanel.getChildren().add(scoreLabel);
    	
		// Add the Action Panel and Button/Score Panel to one Main Panel
		initGameOverScreen();
		BorderPane mainPanel = new BorderPane();
		mainPanel.setLeft(actionPanel);
		mainPanel.setRight(buttonPanel);
		
		// Prepare the Scene and Stage
		Scene scene = new Scene(mainPanel, 670, 1139);
		primaryStage.setTitle("Lava Sea Snake");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
		
		// Ensure key events will be listened to 
		actionPanel.requestFocus();
	}
	
	// Initialize the Game Over Screen, but set it to invisible
	public void initGameOverScreen(){ 
		gameOverLabel = new Label("Game Over");
		gameOverLabel.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 60));
		gameOverLabel.setTextFill(Color.PURPLE);
		gameOverLabel.relocate(198, 280);
		gameOverLabel.setVisible(false);
		gameOverScoreTitle = new Label("Final Score: ");
		gameOverScoreTitle.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 50));
		gameOverScoreTitle.setTextFill(Color.PURPLE);
		gameOverScoreTitle.relocate(177, 340);
		gameOverScoreTitle.setVisible(false);
		gameOverScoreLabel = new Label();
		gameOverScoreLabel.setFont(Font.font("Times New Roman", FontWeight.MEDIUM, 50));
		gameOverScoreLabel.setTextFill(Color.PURPLE);
		gameOverScoreLabel.relocate(585, 340);
		gameOverScoreLabel.setVisible(false);
		
		actionPanel.getChildren().addAll(gameOverLabel, gameOverScoreTitle, gameOverScoreLabel);
	}
	// Display the game over screen
	public void showGameOver(){
		gameOverScoreLabel.setText(Long.toString(score));
		btReset.setVisible(true);
		gameOverLabel.setVisible(true);
		gameOverScoreLabel.setVisible(true);
		gameOverScoreTitle.setVisible(true);
		animateSnake.stop();
	}
	// Handles the frame-by-frame animation of the Snake object
	class SnakeAnimateHandler implements EventHandler<ActionEvent>{
		private Node collidedObject;
		private Iterator<SnakeCell> snakeBodyIt;
		
		public void handle(ActionEvent e){
		
			if (player1.getDirection() != SnakeMovement.Direction.STATIONARY){
				// Remove the Snake from view
				snakeBodyIt = player1.getSnakeBody().iterator();
				while (snakeBodyIt.hasNext()){				
					actionPanel.getChildren().remove(snakeBodyIt.next());
				}
				
				// Animate the Snake. Detect collisions
				collidedObject = player1.animate();
				if (collidedObject != null){
					// Reposition and reset the fish if snake eats it
					if (collidedObject instanceof Fish){
						score += ((Fish)collidedObject).getPointValue();
						scoreLabel.setText(Long.toString(score));
						((Fish)collidedObject).setNewFishType();
						((Fish)collidedObject).reposition();
					}
					// All other objects the snake can collide with are obstacles. If a collision occurs, game over
					else{	
						showGameOver();
					}
				}
				
				snakeBodyIt = player1.getSnakeBody().iterator();
				while (snakeBodyIt.hasNext()){				// Draw the snake in its new position on the panel
					actionPanel.getChildren().add(snakeBodyIt.next());
				}
			}
		}
	}
	class PlayPausedHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			switch(animateSnake.getStatus()){
				case PAUSED:
					animateSnake.play();
					actionPanel.requestFocus();
					break;
				case RUNNING:
					animateSnake.pause();
			}
		}
	}
	class QuitHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			showGameOver();
			actionPanel.requestFocus();
		}
	}
	class ResetHandler implements EventHandler<ActionEvent>{
		Iterator<SnakeCell> snakeBodyIt;
		
		public void handle(ActionEvent e){	
			// Remove all text from the Game Over Screen 
			gameOverLabel.setVisible(false);
			gameOverScoreLabel.setVisible(false);
			gameOverScoreTitle.setVisible(false);
			
			// Reset Snake object to its original position and length
			snakeBodyIt = player1.getSnakeBody().iterator();
			while(snakeBodyIt.hasNext()){
				actionPanel.getChildren().remove(snakeBodyIt.next());
			}
			player1.getSnakeBody().clear();
			player1.getSnakeBody().addFirst(new SnakeCell(grid.getXWindowCoordinates(5), grid.getYWindowCoordinates(5), grid.getSeparation(), Color.BLACK));
			actionPanel.getChildren().add(player1.getSnakeBody().getFirst());
			player1.initialize();

			// Reset Score and hide the reset button
			score = 0;
			scoreLabel.setText(Long.toString(score));
			animateSnake.play();
			btReset.setVisible(false);
			actionPanel.requestFocus();
		}
	}
	// Required entry point for launch on the IDE
	public static void main(String[] args){
		launch(args);
	}
}