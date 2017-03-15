package dkeep.logic;
import java.util.*;
 
public class Map {

	protected char[][] map;
	protected int[] key;
	
	public Map(char[][] map, int[] key){
		this.map = map;
		this.key = key;
	}

	public char[][] getMap(){
		return map;
	}
	public int[] getKey(){
		return key;
	}
	
	public int[] getHeroPos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'H'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	public int[] getGuardPos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'G'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	public int[] getOgrePos(){
		int[] pos = new int[2];
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				if(map[i][j] == 'O'){
					pos[0] = i;
					pos[1] = j;
					return pos;
				}
			}
		}
		return pos;
	}
	
	public void openDoor() {
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 'I')
                    map[i][j] = 'S';
            }
		}
	}
	
	public boolean areDoorsOpen(){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
                if(map[i][j] == 'S')
                    return true;
            }
		}
		return false;
	}

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
		if(x == key[0] && y == key[1])
			return true;
		return false;
	}
	
	public boolean isS(int x, int y){
		if(map[x][y] == 'S')
			return true;
		return false;
	}

	public Map nextMap() {
		return null;
	}

}
