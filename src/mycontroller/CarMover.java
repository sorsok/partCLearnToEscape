package mycontroller;

import java.util.ArrayList;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class CarMover {
	private Boolean previousTurnRight = false;
	private Boolean previousAccelerateForward = false;
	private final float WALL_MARGIN = 0.2f;
	public ArrayList<Boolean> getInstructions(ArrayList<Coordinate> path, GameState gameState){
		ArrayList<Boolean> instructions = new ArrayList<>();
		Coordinate firstDest = path.get(0);
		if (path.get(0).equals(gameState.carState.position)){
			instructions.add(null);
			instructions.add(null);
			return instructions;
		}
		ArrayList<Float> adjustedDest  = adjustAwayFromWall(firstDest, gameState);
		instructions.add(PhysicsCalculations.acceleratingForward(gameState.carState.position.x, gameState.carState.position.y,
				adjustedDest.get(0), adjustedDest.get(1), gameState.carState.angle));
		instructions.add(PhysicsCalculations.getTurningRight(gameState.carState.position.x, gameState.carState.position.y,
				adjustedDest.get(0), adjustedDest.get(1), gameState.carState.angle, this.previousAccelerateForward));
		previousAccelerateForward = instructions.get(0);
		previousTurnRight = instructions.get(1);
		//System.out.println(instructions);
		return instructions;
	}
	
	private ArrayList<Float> adjustAwayFromWall(Coordinate c, GameState gameState){
		ArrayList<Float> output = new ArrayList<>();
		float newX = c.x;
		float newY = c.y;
		for (WorldSpatial.Direction d: WorldSpatial.Direction.values()){
			Coordinate newCoordinate;
			switch(d) {
				case EAST:
					newCoordinate = new Coordinate(c.x + 1, c.y);
					if(gameState.combinedMap.get(newCoordinate) != null && gameState.combinedMap.get(newCoordinate).getType() == MapTile.Type.WALL){
						newX-=WALL_MARGIN;
					}
					break;
				case WEST:
					newCoordinate = new Coordinate(c.x - 1, c.y);
					if(gameState.combinedMap.get(newCoordinate) != null && gameState.combinedMap.get(newCoordinate).getType() == MapTile.Type.WALL){
						newX+=WALL_MARGIN;
					}
					break;
				case NORTH:
					newCoordinate = new Coordinate(c.x, c.y + 1);
					if(gameState.combinedMap.get(newCoordinate) != null && gameState.combinedMap.get(newCoordinate).getType() == MapTile.Type.WALL){
						newY-=WALL_MARGIN;
					}
					break;
				case SOUTH:
					newCoordinate = new Coordinate(c.x, c.y - 1);
					if(gameState.combinedMap.get(newCoordinate) != null && gameState.combinedMap.get(newCoordinate).getType() == MapTile.Type.WALL){
						newY+=WALL_MARGIN;
					}
					break;
				default:
					System.out.println("not a direction");	
			}
		}
		output.add(newX);
		output.add(newY);
		return output;	
	}

}
