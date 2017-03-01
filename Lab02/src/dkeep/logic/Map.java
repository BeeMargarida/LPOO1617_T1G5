package dkeep.logic;

public abstract class Map {
	
	protected char[][] map;
	
	
	public char[][] getMap(){
		return map.clone();
	}
	
	public boolean isFree(int x, int y) {
		if(map[x][y] == 'X'){
			return false;
		}
		else if(map[x][y] == 'I'){
			return false;
		}
		else if(map[x][y] == 'S'){ 	//Is this right?
			return true;
		}
		return true;
	}
	
	public boolean isS (int x, int y) {
		if(map[x][y] == 'S'){
			return true;
		}
		return false;
	}
	
	public abstract Map nextMap();

}
