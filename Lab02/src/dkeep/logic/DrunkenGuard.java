package dkeep.logic;
 
import java.util.concurrent.ThreadLocalRandom;

public class DrunkenGuard extends Guard {
	
	private boolean asleep;
	private char asleepChar;
	
	public DrunkenGuard(char symbol, int x, int y,char[] path){
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.path = path;
		this.isOverKey = false;
		this.stunned = false;
		this.turns = 0;
		this.weapon = null;
		i = -1;
		asleep = false;
		asleepChar = 'g';
	}

	@Override
	public int[] movement() {
		char[] behaviour = {'n','s','r'}; //n-normal, s-sleep, r - awake and reverse;
		int choice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		char beh = behaviour[choice];
		if(beh == 'n'){
			asleep = false;
			return normalMovement();
		}
		else if (beh == 's'){
			asleep = true;
			int[] mov = {x,y};
			return mov;
		}
		else {
			asleep = false;
			return reverseMovement();
		}
	}

	@Override
	public int[] action() {
		return null;
	}

	@Override
	public char getSymbol() {
		if(asleep){
			return asleepChar;
		}
		else
			return symbol;
	}

}
