package dkeep.logic;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DungeonLogic extends Logic {

	public DungeonLogic() {
		Weapon weapon = null;
		hero = new Hero('H',1,1,weapon);
		enemies = new ArrayList<Character>();
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		//Choosing one of 3 guards
		char[] possibleEnemies = {'r','d','s'};
		int choice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		char pos = possibleEnemies[choice];
		if(pos == 'r')
			enemies.add(new RookieGuard('G',1,8, path));
		else if(pos == 'd')
			enemies.add(new DrunkenGuard('D',1,8,path));
		else if(pos == 's')
			enemies.add(new SuspiciousGuard('U',1,8,path));
		isOver = false;
		victory = false;
	}

	public void gameplay(char dir, Map map) {
		int[] enemymov = enemies.get(0).movement();
		enemies.get(0).setX(enemymov[0]);
		enemies.get(0).setY(enemymov[1]);

		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if((enemies.get(0).getSymbol() != 'g') && colideEnemy(heromov[0],heromov[1],enemies))	
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
