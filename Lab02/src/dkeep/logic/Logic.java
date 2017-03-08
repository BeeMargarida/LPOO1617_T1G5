package dkeep.logic;
import java.util.*;
 
public abstract class Logic {
	
	protected Hero hero;
	protected Character enemies[];
	protected boolean isOver;
	protected boolean victory;
	protected Weapon weapons[];
	
	public boolean colideEnemy(int x, int y) {
		for(int i = 0; i < enemies.length; i++){
			if(x == enemies[i].getX() - 1 || x == enemies[i].getX() + 1 || y == enemies[i].getY() - 1 || y == enemies[i].getY() + 1){
				return true;
			}
		}
		return false;
	}
	
	public void setGameOver(){
		isOver = true;
	}
	public abstract void gameplay(char dir, Map map);
	
	public abstract Logic nextLogic();
	
	public boolean gameOver() {
		return isOver;
	}
	
	public abstract boolean getVictory();

}
