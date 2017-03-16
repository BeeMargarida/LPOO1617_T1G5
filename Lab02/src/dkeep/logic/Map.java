package dkeep.logic;
import java.util.*;
 
public abstract class Map {

	protected char[][] map;
	protected int[] key;


	public char[][] getMap(){
		return map;
	}
	public int[] getKey(){
		return key;
	}
	public abstract void openDoor();

	public boolean isFree(int x, int y) { 
		if(map[x][y] == 'X'){
			return false;
		} 
		else if(map[x][y] == 'I'){
			return false;
		}
		else if(map[x][y] == 'S'){
			return false;
		}
		return true;
	}

	public boolean isKey(int x, int y){
		if(x == key[0] && y == key[1])
			return true;
		return false;
	}
	
	public boolean isS(int x, int y){
		if(map[x][y] == 'S')
			return true;
		return false;
	}
	
	public boolean isI(int x, int y){
		if(map[x][y] == 'I')
			return true;
		return false;
	}

	public abstract Map nextMap();

}
