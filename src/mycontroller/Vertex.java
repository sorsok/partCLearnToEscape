package mycontroller;

import tiles.MapTile;
import tiles.TrapTile;
import utilities.Coordinate;

public class Vertex {
    final private Coordinate coordinate;
    final private MapTile tile;
    final private String id;
    private int edgesIn;;


    public Vertex(Coordinate coordinate, MapTile tile) {
        this.coordinate = coordinate;
        this.tile = tile;
        this.id = coordinate.toString();
        edgesIn = 0;
    }
    
    public void increaseEdgesIn(){
    		edgesIn++;
    }
    public int getEdgesIn() {
    		return edgesIn;
    }
    public Coordinate getCoordinate() {
    		return coordinate;
    }
    
    public int getPenalty() {
    		switch(tile.getType()) {
    		case ROAD:
    			return 1;
    		case START:
    			return 1;
    		case FINISH:
    			return 1;
    		case TRAP:
    			if(((TrapTile) tile).getTrap().equals("lava")) {
    				return 20;
    			}
    			else {
    				return 1;
    			}
    		case WALL:
    			return Integer.MAX_VALUE;
    		case EMPTY:
    			return Integer.MAX_VALUE;
    		case UTILITY:
    			return Integer.MAX_VALUE;
    		default:
    			return Integer.MAX_VALUE;
    		}

    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
    		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;

        if (this.coordinate.equals(other.getCoordinate())) {
        		return true;
        }
        return false;
    }


}