package dkeep.logic;

import java.util.ArrayList;

public class KeepLogic extends Logic {

	public KeepLogic(Map map, int[] heropos, boolean armedHero, int option) {
		/*HERO NOT ARMED*/ 
		super(new Hero('A', heropos[0], heropos[1], null));   //7,1  //testes
		if(armedHero){
			hero.weapon = new Club('*','$',heropos[0],heropos[1]);
			hero.weapon.setNotValid();
		}
		enemies = new ArrayList<Character>();

		/*OGRE WITHOUT WEAPON*/
		int[] ogrePos = map.getOgrePos();
		/*OGRE WITH WEAPON*/
		//Weapon weaponE = new Club('*','$',2,4);
		//enemies.add(new CrazyOgre('O', ogrePos[0], ogrePos[1], weaponE));   //1,4  //testes

		/*SEVERAL OGRES*/
		for(int i = 0; i < option; i++) {
			Weapon weaponE = new Club('*','$',ogrePos[0],ogrePos[1]); 
			enemies.add(new CrazyOgre('O',ogrePos[0], ogrePos[1], weaponE));
			enemies.get(i).weapon.setNotValid();
		}
	}

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

	public void enemyWeaponMovement(char dir, Map map){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).weapon != null){
				int[] weaponmov = enemies.get(i).weapon.swing(enemies.get(i).getX(), enemies.get(i).getY());
				System.out.println(weaponmov[0] + " -- " + weaponmov[1]);
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

	public void moveHero(char dir, Map map) {
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(colideEnemy(heromov[0],heromov[1],enemies)){	
				isOver = true;
			}
			else if(this.getWeapons().size() != 0){
				if(colideWeapon(hero.getX(), hero.getY(), this.getWeapons())){	
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

	public void heroWeaponMovement(char dir, Map map){
		if(hero.weapon == null)
			return;
		int[] weaponmov = hero.weapon.swing(hero.getX(), hero.getY());
		if(weaponmov[0] < 0 || weaponmov[1] < 0 || weaponmov[0] > map.getHeight() || weaponmov[1] > map.getWidth()){
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
				if(colideWeapon(enemies.get(i).getX(), enemies.get(i).getY(),heroweapon)){
					enemies.get(i).gotStunned();
				}
			}
		} 
		else
			hero.weapon.setNotValid();
	}

	public void gameplay(char dir, Map map) {
		//enemy movement
		enemyMovement(dir,map);
		//System.out.println("chegou aqui");
		//enemy weapon
		enemyWeaponMovement(dir,map);
		//System.out.println("chegou aqui2");
		//hero movement
		moveHero(dir,map);
		//System.out.println("chegou aqui3");
		//hero weapon
		heroWeaponMovement(dir,map);
		//System.out.println("chegou aqui4");
	}

	public Logic nextLogic(Map map, int option) {
		return null;
	}

	public boolean getVictory() {
		return victory;
	}

}
