package dkeep.logic;
 
import java.util.concurrent.ThreadLocalRandom;

public class Club extends Weapon {

	public Club(char symbol, char secsymbol, int x, int y) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.secsymbol = secsymbol;
		this.above = false;
	}

	@Override
	public int[] swing(int x, int y) { //x and y are the enemy's or hero's coordenates
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
		else {
			int[] mov = {x+1,y};
			return mov;
		}
	}

}
