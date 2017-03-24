package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * KeepLogic is a class that extends Logic and it's function is to deal with the logic of the Keep level. It contains all the specific
 * functions that deal with the hero and the type of enemies of its level. The big difference in this class, compared to the other, is
 * the existence of armed ogres, a possible armed hero and a key instead of a lever.
 * @see Logic
 */
public class KeepLogic extends Logic implements Serializable{

	/**
	 * Constructor of KeepLogic. It calls the Logic constructor, then adds a weapon to the hero if the armedHero variable is true. Also adds
	 * the number of enemies(CrazyOgre) correspondent to the option value.
	 * @param map Map of the current level
	 * @param heropos starting position of the hero
	 * @param armedHero flag that indicates if the hero is armed or not
	 * @param option number of enemies
	 */
	public KeepLogic(Map map, int[] heropos, boolean armedHero, int option) {
		super(new Hero('A', heropos[0], heropos[1], null));
		if(armedHero){
			hero.weapon = new Club('*','$',heropos[0],heropos[1]);
			hero.weapon.setNotValid();
		}
		enemies = new ArrayList<Character>();

		int[] ogrePos = map.getOgrePos();
		for(int i = 0; i < option; i++) {
			Weapon weaponE = new Club('*','$',ogrePos[0],ogrePos[1]); 
			enemies.add(new CrazyOgre('O',ogrePos[0], ogrePos[1], weaponE));
			enemies.get(i).weapon.setNotValid();
		}
	}

	/**
	 * Handles the enemies movement. Requests a possible position of each enemy, then checks if such position is valid (doesn't overlap any
	 * of the static elements of the game). It also checks if the enemy is stunned and, if it is, it will not move.
	 * @param dir char that correspond to the direction of the hero movement chosen by the player
	 * @param map Object Map that corresponds to the current map of the game
	 */
	public void enemyMovement(char dir, Map map) {
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).stunned){
				if(enemies.get(i).turns == 1){
					enemies.get(i).backToNormal();
				}
				else {
					enemies.get(i).turns -= 1;
					break;
				}
			}
			int[] enemymov = enemies.get(i).movement();
			if(map.isFree(enemymov[0],enemymov[1])){
				if(map.isKey(enemymov[0],enemymov[1])){
					enemies.get(i).isOverKey = true;
				}
				else {
					enemies.get(i).isOverKey = false;
				}
				enemies.get(i).setX(enemymov[0]);
				enemies.get(i).setY(enemymov[1]);		
			}
		}
	}

	/**
	 * Handles the enemies' weapons movement. Request a possible position of each enemies' weapon, then checks if such position is valid (doesn't overlap any
	 * of the static elements of the game). It also checks if the enemies' weapon is above and, if it is, it will change it's flag accordingly.
	 * @param dir char that correspond to the direction of the hero movement chosen by the player
	 * @param map Object Map that corresponds to the current map of the game
	 */
	public void enemyWeaponMovement(char dir, Map map){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).weapon != null){
				int[] weaponmov = enemies.get(i).weapon.swing(enemies.get(i).getX(), enemies.get(i).getY());
				if(map.isFree(weaponmov[0], weaponmov[1])){
					if(map.isKey(weaponmov[0], weaponmov[1])){
						enemies.get(i).weapon.above = true;
					} 
					else
						enemies.get(i).weapon.above = false;
					enemies.get(i).weapon.setValid();
					enemies.get(i).weapon.setX(weaponmov[0]);
					enemies.get(i).weapon.setY(weaponmov[1]);
				}
				else
					enemies.get(i).weapon.setNotValid();
			}
		}
	}

	/** 
	 * {@inheritDoc}
	 * In this level the collision of the hero with the enemies' weapons is made.
	 */
	public void moveHero(char dir, Map map) {
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(collideEnemy(heromov[0],heromov[1],enemies)){	
				isOver = true;
			}
			else if(this.getWeapons().size() != 0){
				if(collideWeapon(hero.getX(), hero.getY(), this.getWeapons())){	
					isOver = true;
					hero.setX(heromov[0]);
					hero.setY(heromov[1]);
					return;
				}
			}
			if(map.isKey(heromov[0], heromov[1])){
				hero.setKeyTrue();
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}	
		if(map.isI(heromov[0],heromov[1]) && hero.hasKey()){
			map.openDoor();
			return;
		}
		if(map.isS(heromov[0], heromov[1])){
			victory = true;
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
			return;
		}
	}

	/**
	 * Handles the hero's weapon movement. Request a possible position of the hero's weapon, then checks if such position is valid (doesn't overlap any
	 * of the static elements of the game). It checks if the hero's weapon is above and if is valid and, if it is, it will change it's flag accordingly.
	 * Furthermore, it checks if the hero's weapon stuns any of the guards.
	 * @param dir char that correspond to the direction of the hero movement chosen by the player
	 * @param map Object Map that corresponds to the current map of the game
	 */
	public void heroWeaponMovement(char dir, Map map){
		if(hero.weapon == null)
			return;
		int[] weaponmov = hero.weapon.swing(hero.getX(), hero.getY());
		if(weaponmov[0] < 0 || weaponmov[1] < 0 || weaponmov[0] >= map.getHeight() || weaponmov[1] >= map.getWidth()){
			hero.weapon.setNotValid();
			return;
		}
		if(map.isFree(weaponmov[0], weaponmov[1])){
			if(map.isKey(weaponmov[0], weaponmov[1]) && !hero.hasKey()){
				hero.weapon.above = true;
			} 
			else
				hero.weapon.above = false;
			hero.weapon.setValid();
			hero.weapon.setX(weaponmov[0]);
			hero.weapon.setY(weaponmov[1]);

			//hero weapon collision with enemies
			ArrayList<Weapon> heroweapon = new ArrayList<Weapon>();
			heroweapon.add(hero.weapon);
			for(int i = 0; i < enemies.size(); i++){
				if(collideWeapon(enemies.get(i).getX(), enemies.get(i).getY(),heroweapon)){
					enemies.get(i).gotStunned();
				}
			}
		} 
		else
			hero.weapon.setNotValid();
	}
 
	/**
	 * {@inheritDoc}
	 */
	public void gameplay(char dir, Map map) {
		enemyMovement(dir,map);
		enemyWeaponMovement(dir,map);
		moveHero(dir,map);
		heroWeaponMovement(dir,map);
	}

	/**
	 * {@inheritDoc}
	 */
	public Logic nextLogic(Map map, int option) {
		return null;
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean getVictory() {
		return victory;
	}

}