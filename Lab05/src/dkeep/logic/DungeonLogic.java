package dkeep.logic;

import java.util.ArrayList; 
import java.util.concurrent.ThreadLocalRandom;

public class DungeonLogic extends Logic {

	public DungeonLogic(Map map, int[] heropos, int option) {
		super(new Hero('H',heropos[0],heropos[1],null));
		
		enemies = new ArrayList<Character>();
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		
		int[] guardPos = map.getGuardPos();
		if(option == 1)
			enemies.add(new RookieGuard('G',guardPos[0],guardPos[1], path));
		else if(option == 2)
			enemies.add(new DrunkenGuard('D',guardPos[0],guardPos[1],path));
		else if(option == 3)
			enemies.add(new SuspiciousGuard('U',guardPos[0],guardPos[1],path));
		
	}

	public void moveHero(char dir, Map map){
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		if(map.isS(heromov[0], heromov[1])){
			victory = true;
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		
		if(collideEnemy(hero.getX(),hero.getY(),enemies))
			isOver = true; 
	}
	
	public void gameplay(char dir, Map map) {
	
		int[] enemymov = enemies.get(0).movement();
		enemies.get(0).setX(enemymov[0]);
		enemies.get(0).setY(enemymov[1]);
		
		moveHero(dir, map);
	}


	public Logic nextLogic(Map map, int option){
		int[] heropos = map.getHeroPos();
		return new KeepLogic(map, heropos, true, option);
	}

}
