package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.concurrent.ThreadLocalRandom;

/**
 * DungeonLogic is a class that extends Logic and it's function is to deal with the logic of the Dungeon level. It contains all the specific
 * functions that deal with the hero and the type of enemies of its level. The big difference in this class, compared to the other, is
 * the existence of three guards, each with a different behaviour. This level only has a lever.
 * @see Logic
 */
public class DungeonLogic extends Logic implements Serializable{

	/**
	 * Constructor of DungeonLogic. It calls the Logic constructor, then adds one enemy, which behaviour is chosen according to the option selected by the user.
	 * The path is always the same.
	 * @param map Map of the current level
	 * @param heropos starting position of the hero
	 * @param option refers to the behaviour of the guard
	 */
	public DungeonLogic(Map map, int[] heropos, int option) {
		super(new Hero('H',heropos,null));
		enemies = new ArrayList<Character>();
		char[] path = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
		if(option == 1)
			enemies.add(new RookieGuard('G',map.getGuardPos(), path));
		else if(option == 2)
			enemies.add(new DrunkenGuard('D',map.getGuardPos(),path));
		else if(option == 3)
			enemies.add(new SuspiciousGuard('U',map.getGuardPos(),path));
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean moveHero(char dir, Map map){
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				return true;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		if(map.isS(heromov[0], heromov[1])){
			victory = true;
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		if(collideEnemy(new int[] {hero.getX(),hero.getY()},enemies))
			isOver = true; 
		return true;
	}
	/**
	 * {@inheritDoc}
	 * In this level, only the enemy and hero movements are made because there are no weapons.
	 */
	public void gameplay(char dir, Map map) {
		int[] enemymov = enemies.get(0).movement();
		enemies.get(0).setX(enemymov[0]);
		enemies.get(0).setY(enemymov[1]);
		moveHero(dir, map);
	}

	/**
	 * {@inheritDoc}
	 */
	public Logic nextLogic(Map map, int option){
		int[] heropos = map.getHeroPos();
		return new KeepLogic(map, heropos, true, option);
	}

}
