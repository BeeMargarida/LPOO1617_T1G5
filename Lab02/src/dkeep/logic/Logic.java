package dkeep.logic;
import java.util.*;
 
public abstract class Logic {
	
	protected Character hero;
	//protected List<Enemy> enemy;
	protected Enemy enemy[];
	protected boolean isOver;
	protected boolean openDoors; //if the hero has the key
	
	public boolean colideEnemy(int x, int y) {
		for(int i = 0; i < enemy.length; i++){
			if(x == enemy[i].getX() - 1 || x == enemy[i].getX() + 1 || y == enemy[i].getY() - 1 || y == enemy[i].getY() + 1){
				return true;
			}
		}
		return false;
	}
	
	
	public void movement(char dir, Map map) { //hero movement 
		int x = hero.getX();
		int y = hero.getY();
		if(dir == 'w'){ 
			if(colideEnemy(x-1,y)){
				return;
			}
			if(map.isKey(x-1, y)){
				///openDoors = true;
				map.openDoor();
			}
			else if(map.isFree(x-1,y)){
				if(map.isS(x-1, y, openDoors)){
					isOver = true;
				}
				hero.setX(x-1);
			}
		}
		else if(dir == 'a') {
			if(colideEnemy(x,y-1)){
				return;
			}
			if(map.isKey(x, y-1)){
				//openDoors = true;
				map.openDoor();
			}
			else if(map.isFree(x,y-1)){
				if(map.isS(x, y-1, openDoors)){
					isOver = true;
				}
				hero.setY(y-1);
			}
		}
		else if(dir == 'd'){
			//boolean b = colideEnemy(x,y+1);
			if(colideEnemy(x,y+1)){
				return;
			}
			if(map.isKey(x, y+1)){
				//openDoors = true;
				map.openDoor();
			}
			else if(map.isFree(x, y+1)){
				if(map.isS(x, y+1, openDoors)){
					isOver = true;
				}
				hero.setY(y+1);
			}
		}
		else if(dir == 's'){
			if(colideEnemy(x+1,y)){
				return;
			}
			if(map.isKey(x+1, y)){
				//openDoors = true;
				map.openDoor();
			}
			else if(map.isFree(x+1, y)){
				if(map.isS(x+1, y, openDoors)){
					isOver = true;
				}
				hero.setX(x+1);
			}
		}
	}
	
	public boolean gameOver() {
		return isOver;
	}

}
