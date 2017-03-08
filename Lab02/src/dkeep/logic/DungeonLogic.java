package dkeep.logic;

import java.util.concurrent.ThreadLocalRandom;

public class DungeonLogic extends Logic {

	public DungeonLogic() {
		hero = new Hero('H',1,1);
		//map = new DungeonMap();
		enemies = new Character[1];
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		//Choosing one of 3 guards
		char[] possibleEnemies = {'r','d','s'};
		int choice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		char pos = possibleEnemies[choice];
		if(pos == 'r')
			enemies[0] = new RookieGuard('G',1,8, path);
		else if(pos == 'd')
			enemies[0] = new DrunkenGuard('D',1,8,path);
		else if(pos == 's')
			enemies[0] = new SuspiciousGuard('S',1,8,path);
		weapons = null;
		isOver = false;
		victory = false;
	}

	public void gameplay(char dir, Map map) {
		int[] enemymov = enemies[0].movement();
		enemies[0].setX(enemymov[0]);
		enemies[0].setY(enemymov[1]);

		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if((enemies[0].getSymbol() != 'g') && ((heromov[0] == enemymov[0] && heromov[1] == enemymov[1]) || (heromov[0]+1 == enemymov[0] && heromov[1] == enemymov [1]) || (heromov[0]-1 == enemymov[0] && heromov[1] == enemymov [1]) || (heromov[1]+1 == enemymov[1] && heromov[0] == enemymov [0]) || (heromov[1]-1 == enemymov[1] && heromov[0] == enemymov [0])))
				isOver = true; 
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return;
			}
			else if(map.isS(heromov[0], heromov[1])){
				victory = true;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
	}

	public boolean getVictory(){
		return victory; 
	}

	public Logic nextLogic(){
		return new KeepLogic();
	}

}
