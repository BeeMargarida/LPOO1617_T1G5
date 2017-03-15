package dkeep.logic;
import java.util.ArrayList; 

public abstract class Logic {

	protected Hero hero;
	protected ArrayList<Character> enemies;
	protected boolean isOver;
	protected boolean victory;
	
	public CellPosition getHeroPosition(){
		CellPosition c = new CellPosition(hero.getX(),hero.getY());
		return c;
	}


	public boolean colideEnemy(int x, int y, ArrayList<Character> vector) {
		for(int i = 0; i < vector.size(); i++){
			if((vector.get(i).stunned == false) && ((x == vector.get(i).getX() && y == vector.get(i).getY()) || (x+1 == vector.get(i).getX() && y == vector.get(i).getY()) || (x-1 == vector.get(i).getX() && y == vector.get(i).getY()) || (y+1 == vector.get(i).getY() && x == vector.get(i).getX()) || (y-1 == vector.get(i).getY() && x == vector.get(i).getX()))){
				return true;
			}
		}
		return false;
	}
	public boolean colideWeapon(int x, int y, ArrayList<Weapon> vector) {
		for(int i = 0; i < vector.size(); i++){
			if((x == vector.get(i).getX() && y == vector.get(i).getY()) || (x+1 == vector.get(i).getX() && y == vector.get(i).getY()) || (x-1 == vector.get(i).getX() && y == vector.get(i).getY()) || (y+1 == vector.get(i).getY() && x == vector.get(i).getX()) || (y-1 == vector.get(i).getY() && x == vector.get(i).getX())){
				return true;
			}
		}
		return false;
	}

	public void setGameOver(){
		isOver = true;
	}

	public ArrayList<Weapon> getWeapons(){
		ArrayList<Weapon> res = new ArrayList<Weapon>();
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).weapon != null)
				res.add(enemies.get(i).weapon);
		}
		return res;
	}

	public abstract void gameplay(char dir, Map map);

	public abstract Logic nextLogic();

	public boolean gameOver() {
		return isOver;
	}

	public abstract boolean getVictory();

}
