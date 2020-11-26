package game;

import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import java.nio.file.Path;
import java.awt.Panel;
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
import javafx.scene.shape.Rectangle;

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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import java.util.Comparator;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.*;

public class Game extends Application {
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
	
	final static String SPRITESHEET_DIR = "data\\spritesheet.png";
	
	final static String WEAPON_HEADER_DIR = "data\\itemdata\\weapons\\weaponheader.txt";
	final static String ARMOR_HEADER_DIR = "data\\itemdata\\armor\\armorheader.txt";
	final static String ACTOR_HEADER_DIR = "data\\entitydata\\actors\\actorheader.txt";
	final static String TILE_HEADER_DIR = "data\\entitydata\\tiledata\\tileheader.txt";
	final static String SPRITE_HEADER_DIR = "data\\spritedata\\spriteheader.txt";
	final static String LEVEL_HEADER_DIR = "data\\leveldata\\levelheader.txt";
	
	final static String WEAPON_DEFS = fileToString(new File(WEAPON_HEADER_DIR));
	final static String ARMOR_DEFS = fileToString(new File(ARMOR_HEADER_DIR));
	final static String ACTOR_DEFS = fileToString(new File(ACTOR_HEADER_DIR));
	final static String TILE_DEFS = fileToString(new File(TILE_HEADER_DIR));
	final static String SPRITE_DEFS = fileToString(new File(SPRITE_HEADER_DIR));
	final static String ALL_DEFS = WEAPON_DEFS + ARMOR_DEFS + ACTOR_DEFS + TILE_DEFS + SPRITE_DEFS;
	
	final static Image spritesheet = new Image(new File(SPRITESHEET_DIR).toURI().toString());
	
	Level level;
	Actor player;
	int turn = 0;
	double[] cameraPos;
	
	public void init() {
		level = new Level(fileToString(new File(LEVEL_HEADER_DIR)), fileToString(new File("data\\leveldata\\level1.csv")));
		player = level.getPlayer();
		cameraPos = new double[2];
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
	
	public static String getDef(String toGet) {
		return extractAttribute(ALL_DEFS, toGet);
	}
	
	public void renderScreen(GraphicsContext gc, double[] cameraPos) {
		
		level.getEntities().sort(new Comparator<Entity>() {
			public int compare(Entity e1, Entity e2) {
				return(e1.getSprite().getRenderPriority() - e2.getSprite().getRenderPriority());
			}
		});
		
		gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		for(Entity e: level.getEntities()) {
			e.render(gc, cameraPos);
		}
		
	}
	
	public static String extractAttribute(String data, String attribute) {
		if (data.contains(attribute)) {
			int indexStart = data.indexOf(attribute + ":") + attribute.length() + 1;
			int indexEnd = data.indexOf(",", indexStart);
			return(data.substring(indexStart, indexEnd));
		}
		return null;
	}
	
	public void processTurns() {
		player.doTurn();
		
		for(Entity e: level.getEntities()) {
			if(e instanceof Actor && e != player) {
				Actor a = (Actor) e;
				a.setMyTurn(new TurnPass());
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
		
		Turn playerTurn = player.getMyTurn();
		if(playerTurn != null) {
			boolean isLegal;
			if (playerTurn instanceof TurnMove) {
				isLegal = ((TurnMove) playerTurn).isLegal(level);
			} else if(playerTurn instanceof TurnUse) {
				isLegal = ((TurnUse) playerTurn).isLegal();
			} else {
				isLegal = true;
			}
			
			if(isLegal) {
				processTurns();
			} else {
				player.setMyTurn(null);
			}
		}
	}
	
	public void start(Stage stage) {
		Rectangle2D screen = Screen.getPrimary().getBounds();
		Rectangle2D sceneBounds = new Rectangle2D(0, 0, screen.getWidth() / 2, screen.getHeight() / 2);
		
		Group group = new Group();
		Scene scene = new Scene(group, sceneBounds.getWidth(), sceneBounds.getHeight());
		UI ui = new UI(sceneBounds, player);
		GraphicsContext gc = ui.getCanvas().getGraphicsContext2D();
		
		group.getChildren().add(ui.getOverlay());
		
		stage.setResizable(false);
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
				cameraPos[0] = (player.getPosition()[0] * TILE_WIDTH) - ui.getCanvas().getWidth() / 2;
				cameraPos[1] = (player.getPosition()[1] * TILE_HEIGHT) - ui.getCanvas().getHeight() / 2;
				renderScreen(gc, cameraPos);
			}
		}.start();
	}
}
