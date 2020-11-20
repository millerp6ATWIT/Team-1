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
		ArrayList<Item> inventory = new ArrayList<>();
		HashMap<String, Integer> stats = new HashMap<>();
		int[] position = new int[2];
		String name = "Test Actor";
		
		for(String stat: Actor.genericStats) {
			stats.put(stat, -1);
		}

		Actor a1 = new Actor(inventory, stats, position, name);
		Actor a2 = new Actor(inventory, stats, position, name);
		Weapon testWeapon = new Weapon(10, false, a1);
		
		System.out.println(a2.getStats().get("HP"));
		
		a1.setMyTurn(new TurnUse(testWeapon));
		a1.getMyTurn().execute(a1);
		a1.setMyTurn(new TurnUse(testWeapon));
		a1.getMyTurn().execute(a2);
		
		System.out.println(a2.getStats().get("HP"));
	}
	
	public String fileToString(File f) {
		try {
			return Files.readString(Path.of((f.getPath())));
		} catch (Exception e) {
			return "";
		}
	}

}
