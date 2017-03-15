package dkeep.logic;

import java.util.ArrayList; 
import java.util.concurrent.ThreadLocalRandom;

public class DungeonLogic extends Logic {

	public DungeonLogic(Map map, int[] heropos) {
		super(new Hero('H',heropos[0],heropos[1],null));
		
		enemies = new ArrayList<Character>();
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		//Choosing one of 3 guards
		/*
		char[] possibleEnemies = {'r','d','s'};
		int choice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
		char pos = possibleEnemies[choice];
		if(pos == 'r')
			enemies.add(new RookieGuard('G',guardPos[0],guardPos[1], path));
		else if(pos == 'd')
			enemies.add(new DrunkenGuard('D',guardPos[0],guardPos[1],path));
		else if(pos == 's')
			enemies.add(new SuspiciousGuard('U',guardPos[0],guardPos[1],path));
		 */
		int[] guardPos = map.getGuardPos();
		enemies.add(new RookieGuard('G',guardPos[0],guardPos[1], path));  //para testes //1,8
		//enemies.add(new RookieGuard('G', 1, 8, path));
		isOver = false;
		victory = false;
	}

	public void moveHero(char dir, Map map){
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if((enemies.get(0).getSymbol() != 'g') && colideEnemy(heromov[0],heromov[1],enemies)){	
				System.out.println("I was caught");
				isOver = true; 
			}
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return;
			}
			else if(map.isS(heromov[0], heromov[1])){
				victory = true;
				return;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
	}
	
	public void gameplay(char dir, Map map) {
	
		int[] enemymov = enemies.get(0).movement();
		enemies.get(0).setX(enemymov[0]);
		enemies.get(0).setY(enemymov[1]);
		
		moveHero(dir, map);
		/*
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if((enemies.get(0).getSymbol() != 'g') && colideEnemy(heromov[0],heromov[1],enemies)){	
				System.out.println("I was caught");
				isOver = true; 
			}
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return;
			}
			else if(map.isS(heromov[0], heromov[1])){
				System.out.println("You won!");
				victory = true;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		*/
	}


	public Logic nextLogic(Map map){
		int[] heropos = map.getHeroPos();
		return new KeepLogic(map, heropos);
	}

}
