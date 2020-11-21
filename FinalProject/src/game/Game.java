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

public class Game extends Application {
	final int WINDOW_HEIGHT = 256;
	final int WINDOW_WIDTH = 512;
	Level level = new Level(new ArrayList<Entity>());
	public Actor player;
	
	public void init() {
		
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
	
	public void renderEntities(GraphicsContext g) {
		for(Entity e: level.getEntities()) {
			e.render(g);
		}
	}
	
	public static String extractAttribute(String data, String attribute) {
		int indexStart = data.indexOf(attribute + ":") + attribute.length() + 1;
		int indexEnd = data.indexOf(",", indexStart);
		return(data.substring(indexStart, indexEnd));
	}
	
	public void handleInput(KeyCode k) {
		if(k == KeyCode.W) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] - 1;
			player.setPosition(destination);
		} else if(k == KeyCode.A) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] - 1;
			destination[1] = player.getPosition()[1];
			player.setPosition(destination);
		} else if(k == KeyCode.S) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0];
			destination[1] = player.getPosition()[1] + 1;
			player.setPosition(destination);
		} else if(k == KeyCode.D) {
			int[] destination = new int[2];
			destination[0] = player.getPosition()[0] + 1;
			destination[1] = player.getPosition()[1];
			player.setPosition(destination);
		}
	}
	
	public Level initializeLevel() {
		ArrayList<Entity> entities = new ArrayList<>();
		player = new Actor(Game.fileToString(new File("data\\entitydata\\actors\\testenemy.txt")));
		player.setPosition(new int[] {0, 0});
		Level level = new Level(entities);
		level.addEntity(player);
		return level;
	}
	
	public void start(Stage stage) {
		Group group = new Group();
		Scene scene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT);
		Canvas canvas = new Canvas();
		canvas.setWidth(WINDOW_WIDTH);
		canvas.setHeight(WINDOW_HEIGHT);
		GraphicsContext g = canvas.getGraphicsContext2D();
		
		g.setFill(Color.BLACK);
		
		group.getChildren().add(canvas);
		stage.setScene(scene);
		
		level = initializeLevel();
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				handleInput(event.getCode());
			}
		});
		
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long currentTime) {
				g.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
				renderEntities(g);
			}
		};
		timer.start();
		
		stage.show();
	}

}
