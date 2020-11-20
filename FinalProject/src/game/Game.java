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

import java.util.HashMap;

public class Game {

	public static void main(String[] args) {
		String weaponData = Game.fileToString(new File("data\\itemdata\\weapons\\testweapon.txt"));
		String enemyData = Game.fileToString(new File("data\\entitydata\\actors\\testenemy.txt"));
		
		Actor a1 = new Actor(enemyData);
		Actor a2 = new Actor(enemyData);
		Weapon w1 = new Weapon(weaponData);
		
		System.out.println(a2.getStats().get("HP"));
		
		a1.setMyTurn(new TurnUse(w1));
		a1.getMyTurn().execute(a1);
		a1.setMyTurn(new TurnUse(w1));
		a1.getMyTurn().execute(a2);
		
		System.out.println(a2.getStats().get("HP"));
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

}
