package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class SuspiciousGuard extends Guard {
	
	public SuspiciousGuard(char symbol, int x, int y, char[] path) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		this.path = path; //double check this
		i = -1;
	}

	@Override
	public int[] movement() {
		char[] behaviour = {'n','s'}; //n - normal, s - reverse
		int choice = ThreadLocalRandom.current().nextInt(0, 1 + 1);
		char beh = behaviour[choice];
		if(beh == 'n'){
			return normalMovement();
		}
		else {
			return reverseMovement();
		}
	}

	@Override
	public int[] action() {
		return null;
	}

	@Override
	public char getSymbol() {
		return symbol;
	}

}
