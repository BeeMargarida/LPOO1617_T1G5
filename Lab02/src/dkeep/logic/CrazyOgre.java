package dkeep.logic; 

import java.util.concurrent.ThreadLocalRandom;

public class CrazyOgre extends Ogre {

	public CrazyOgre(char symbol, int x, int y, Weapon weapon) {
		this.symbol = symbol;
		this.x = x;
		this.y = y;
		this.isOverKey = false;
		this.overlap = false;
		this.weapon = weapon;
		this.stunned = false;
		this.turns = 0;
	}

	@Override
	public int[] movement() {
		char[] dirpos = {'w','a','s','d'};
		int choice = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		char dir = dirpos[choice];
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
		else{
			int[] mov = {x+1,y};
			return mov;
		}
	}

	@Override
	public char getSymbol() {
		if(isOverKey)
			return '$';
		else if(stunned)
			return '8';
		else
			return symbol;
	}
}
