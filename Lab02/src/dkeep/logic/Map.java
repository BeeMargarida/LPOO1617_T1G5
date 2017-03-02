package dkeep.logic;
import java.util.*;

public abstract class Map {

	protected char[][] map;


	public char[][] getMap(){
		return map;
	}
	public abstract void openDoor();

	public boolean isFree(int x, int y) { 
		if(map[x][y] == 'X'){
			return false;
		} 
		else if(map[x][y] == 'I'){
			return false;
		}
		return true;
	}

	public boolean isKey(int x, int y){
		if(map[x][y] == 'k')
			return true;
		return false;
	}
	
	public boolean isS(int x, int y){
		if(map[x][y] == 'S')
			return true;
		return false;
	}

	public abstract Map nextMap();

}