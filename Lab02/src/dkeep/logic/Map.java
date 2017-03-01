package dkeep.logic;
import java.util.*;

public abstract class Map {
	
	protected char[][] map;
	
	
	public char[][] getMap(){
		/*char[][] m = new char[map.length][map[0].length];
		for(int i = 0; i < map.length; i++){
			m[i] = map[i].clone();
		}
		return m;*/
		return map;
	}
	public void openDoor(){
			for(int i = 0; i < map.length; i++){
				for(int j = 0; j < map[i].length; j++){
					if(map[i][j] == 'I')
						map[i][j] = 'S';
				}
			}
	}
	
	public boolean isFree(int x, int y) { 
		if(map[x][y] == 'X'){
			return false;
		} 
		else if(map[x][y] == 'I'){
			return false;
		}
		else if(map[x][y] == 'k'){ 
			return false;
		}
		else if(map[x][y] == 'S'){ 	//Is this right?
			return true;
		}
		return true;
	}
	
	public boolean isKey(int x, int y){
		if(map[x][y] == 'k')
			return true;
		return false;
	}
	
	public boolean isS (int x, int y, boolean openDoors) {
		if(map[x][y] == 'S'/*'I' && openDoors*/){
			return true;
		}
		return false;
	}
	
	public abstract Map nextMap();

}
