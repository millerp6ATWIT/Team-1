package game;

import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import entity.Actor;
import item.Item;
import item.Weapon;
import turn.TurnUse;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import entity.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import turn.*;
import javafx.scene.text.*;
import javafx.geometry.Pos;

public class Game extends Application {
	final int WINDOW_HEIGHT = 512;
	final int WINDOW_WIDTH = 1024;
	
	Level level;
	Actor player;
	int turn = 0;
	
	public void init() {
		level = new Level(new ArrayList<Entity>());
		player = new Actor(fileToString(new File("data\\entitydata\\actors\\testenemy.txt")));
		player.setPosition(new int[] {0, 0});
		level.addEntity(player);
	}
	
	public void stop() {
		
	}
	
	public static String fileToString(File f) {
		try {
			return Files.readString(Path.of((f.getPath())));
		} catch (Exception e) {
			return "";
		}
	}
	
	public void renderScreen(GraphicsContext gc) {
		gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		for(Entity e: level.getEntities()) {
			e.render(gc);
		}
		
	}
	
	public static String extractAttribute(String data, String attribute) {
		int indexStart = data.indexOf(attribute + ":") + attribute.length() + 1;
		int indexEnd = data.indexOf(",", indexStart);
		return(data.substring(indexStart, indexEnd));
	}
	
	public void processTurns() {
		for(Entity e: level.getEntities()) {
			if(e instanceof Actor) {
				Actor a = (Actor) e;
				a.doTurn();
			}
		}
		
		turn++;
	}
	
	public void handleInput(KeyCode k) {
		if(k == KeyCode.W) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] - 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.A) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] - 1;
			destination[1] = player.getPosition()[1];
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.S) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] + 1;
			player.setMyTurn(new TurnMove(destination, player));
		} else if(k == KeyCode.D) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] + 1;
			destination[1] = player.getPosition()[1];
			player.setMyTurn(new TurnMove(destination, player));
		}
		
		if(player.getMyTurn() != null) {
			processTurns();
		}
	}
	
	public void start(Stage stage) {
		Group group = new Group();
		Scene scene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT);
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Text turnCounter = new Text();
		
		turnCounter.setX(20);
		turnCounter.setY(20);
		turnCounter.setScaleX(2);
		turnCounter.setScaleY(2);
		
		group.getChildren().add(canvas);
		group.getChildren().add(turnCounter);
		stage.setScene(scene);
		stage.show();
		gc.setFill(Color.BLACK);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				handleInput(event.getCode());
			}
		});
		
		new AnimationTimer() {
			public void handle(long now) {
				turnCounter.setText("Turn " + Integer.toString(turn));
				renderScreen(gc);
			}
		}.start();
	}
}
