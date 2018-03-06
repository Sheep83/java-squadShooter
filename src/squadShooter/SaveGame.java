package squadShooter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveGame {
	
	ArrayList<Integer> level;
	String name;
	String mapData = "";
	
	public SaveGame(String name, ArrayList<Integer> level) {
		this.level = level;
		this.name = name;
//		writeToFile();
		
	}
	
	public void writeToFile() {
		
		for(int i = 0; i < level.size(); i++) {
			mapData += level.get(i);
		}
		
		File file = new File(name);
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
