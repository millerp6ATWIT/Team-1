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
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Game extends Application {
	final int WINDOW_HEIGHT = 256;
	final int WINDOW_WIDTH = 512;
	Canvas canvas = new Canvas(WINDOW_HEIGHT, WINDOW_WIDTH);
	
	public static void main(String[] args) {
		Application.launch("");
		
		boolean exit = false;
		while(!exit) {
			
		}
	}
	
	public static String fileToString(File f) {
		try {
			return Files.readString(Path.of((f.getPath())));
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String extractAttribute(String data, String attribute) {
		int indexStart = data.indexOf(attribute + ":") + attribute.length() + 1;
		int indexEnd = data.indexOf(",", indexStart);
		return(data.substring(indexStart, indexEnd));
	}
	
	public void start(Stage stage) {
		Group group = new Group();
		Scene scene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT);
		group.getChildren().add(canvas);
		stage.setScene(scene);
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				System.out.println(1);
			}
		});
		
		stage.show();
	}

}
