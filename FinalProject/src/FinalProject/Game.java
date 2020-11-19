package FinalProject;

import java.nio.file.Path;
import java.io.File;
import java.nio.file.Files;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String fileToString(File f) {
		try {
			return Files.readString(Path.of((f.getPath())));
		} catch (Exception e) {
			return "";
		}
	}

}
