package mycontroller;

import java.util.ArrayList;

import tiles.LavaTrap;
import tiles.MapTile;
import utilities.Coordinate;

public class KeyStrategy implements PathStrategy{
	public ArrayList<Coordinate> findPath(GameState gameState){
		MapTile currTile = gameState.combinedMap.get(gameState.carState.position);
		if (currTile instanceof LavaTrap && ((LavaTrap) currTile).getKey() == gameState.currKey - 1){
			return null;
		}
		Coordinate dest = findDest(gameState);
		if (dest == null){
			return null;
		}
		// search
		ArrayList<Coordinate> path;
		
		// include logic for changing costs depending on current key and other squares etc.
		float lavaCost = 300 - gameState.carState.health;
		float healthCost = gameState.carState.health;
		float grassCost = 120;
		path = AStarSearch.findPath(gameState.carState.position, dest, lavaCost, healthCost, grassCost, gameState);

		if (willSurvive(path, gameState)){
			return path;
		}
		return null;
	}
	
	public Coordinate findDest(GameState gameState){
		Coordinate dest = null;
		for (Coordinate c: gameState.exploredMap.keySet()){
			MapTile t = gameState.exploredMap.get(c);
				if (gameState.currKey == 1){
					if (t.getType().equals(MapTile.Type.FINISH)){
						return c;
					}
				}
				else{
					if (t instanceof LavaTrap){
						if (((LavaTrap) t).getKey() > 0){
							System.out.print("in exploredMap key is: ");
							System.out.println(((LavaTrap) t).getKey());
						}
						if (((LavaTrap) t).getKey() == (gameState.currKey - 1)){
							return c;
						}
					}
				}
			}
		return dest;
	}
	
	private Boolean willSurvive(ArrayList<Coordinate> path, GameState gameState){
		return gameState.carState.health - 2*lavaPathCost(path, gameState) > 0 ? true: false;
	}
	private float lavaPathCost(ArrayList<Coordinate> path, GameState gameState) {
		int lavaCrossed = 0;
		for(Coordinate c: path){
			if(gameState.combinedMap.get(c) instanceof LavaTrap) {
				lavaCrossed ++;
			}
		}
		return lavaCrossed/gameState.maxSpeed * LavaTrap.HealthDelta;
	}
}
