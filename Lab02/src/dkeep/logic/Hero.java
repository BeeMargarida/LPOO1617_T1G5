package dkeep.logic; 

public class Hero extends Character {

	private char dir;
	private boolean hasKey;

	public Hero(char s, int x, int y, Weapon weapon) {
		symbol = s;
		this.x = x;
		this.y = y;
		this.isOverKey = false;
		this.stunned = false;
		this.turns = 0;
		this.weapon = weapon;
		hasKey = false;
	}

	public void setDir(char dir) {
		this.dir = dir;
	}

	public int[] movement(){
		if(dir == 'w'){ 
			int[] mov = {x-1,y};
			return mov;
		}
		else if(dir == 'a') {
			int[] mov = {x,y-1};
			return mov;
		}
		else if(dir == 'd'){
			int[] mov = {x,y+1};
			return mov;
		}
		else if(dir == 's'){
			int[] mov = {x+1,y};
			return mov;
		}
		else {
			int[] mov = {x,y};
			return mov;
		}
	}

	public boolean hasKey(){
		return hasKey;
	}

	public void setKeyTrue(){
		hasKey = true;
	}

	@Override
	public char getSymbol() {
		if(hasKey)
			return 'K';
		else
			return symbol;
	}

}
