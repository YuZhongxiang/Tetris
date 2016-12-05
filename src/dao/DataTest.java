package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.Player;

public class DataTest implements Data {
	
	public DataTest(HashMap<String, String>param) {
		
	}
	
	@Override
	public List<Player> loadData() {
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("Yu", 100));
		players.add(new Player("Yu", 452));
		players.add(new Player("Yu", 3352));
		return players;
	}

	@Override
	public void saveData(Player players) {	
//	    System.out.println();
	}
}
