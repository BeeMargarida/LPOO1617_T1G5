package dkeep.logic;

import java.util.ArrayList;

public class KeepLogic extends Logic {

	public KeepLogic() {
		/*HERO NOT ARMED*/
		//Weapon weapon = null;
		//hero = new Hero('H',7,1, weapon);
		//enemies.add(new CrazyOgre('O',1,4, weapon)); //OGRE WITHOUT WEAPON

		Weapon weaponH = new Club('*','$',7,2);
		hero = new Hero('A',7,1, weaponH);
		enemies = new ArrayList<Character>();

		/*OGRE WITH WEAPON*/
		//Weapon weaponE = new Club('*','$',2,4);
		//enemies.add(new CrazyOgre('O',1,4, weaponE));

		/*SEVERAL OGRES*/
		Weapon weaponE = new Club('*','$',2,4);
		for(int i = 0; i < 2; i++) {
			enemies.add(new CrazyOgre('O',1,4,weaponE));
		}
		isOver = false;
		victory = false;
	}

	@Override
	public void gameplay(char dir, Map map) {
		//enemy
		for(int i = 0; i < enemies.size(); i++){
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
		//weapon
		for(int i = 0; i < enemies.size(); i++){
			boolean set = false;
			if(enemies.get(i).weapon != null){
				while(set == false){
					int[] weaponmov = enemies.get(i).weapon.swing(enemies.get(i).getX(), enemies.get(i).getY());
					if(map.isFree(weaponmov[0], weaponmov[1])){
						set = true;
						if(map.isKey(weaponmov[0], weaponmov[1])){
							enemies.get(i).weapon.above = true;
						} 
						else
							enemies.get(i).weapon.above = false;
						enemies.get(i).weapon.setX(weaponmov[0]);
						enemies.get(i).weapon.setY(weaponmov[1]);
					}
				}
			}
		}
		//hero
		hero.setDir(dir);
		int[] heromov = hero.movement();
		if(map.isFree(heromov[0], heromov[1])){
			if(colideEnemy(heromov[0],heromov[1],enemies)){	
				isOver = true;
			}
			if(this.getWeapons().size() != 0){
				if(colideWeapon(hero.getX(), hero.getY(), this.getWeapons())){	
					isOver = true;
				}
			}
			if(map.isKey(heromov[0], heromov[1])){
				map.openDoor();
				hero.setKeyTrue();
				return;
			}
			else if(map.isS(heromov[0], heromov[1])){
				victory = true;
			}
			hero.setX(heromov[0]);
			hero.setY(heromov[1]);
		}
		if(colideEnemy(heromov[0],heromov[1],enemies)){	
			isOver = true;
		}
		if(this.getWeapons().size() != 0){
			if(colideWeapon(hero.getX(),hero.getY(),this.getWeapons())){	
				isOver = true;
			}
		}
		//hero weapon
		int[] weaponmov = hero.weapon.swing(hero.getX(), hero.getY());
		boolean set = false;
		while(set == false){
			if(map.isFree(weaponmov[0], weaponmov[1])){
				set = true;
				if(map.isKey(weaponmov[0], weaponmov[1])){
					hero.weapon.above = true;
				} 
				else
					hero.weapon.above = false;
				hero.weapon.setX(weaponmov[0]);
				hero.weapon.setY(weaponmov[1]);
			}
		}
	}

	@Override
	public Logic nextLogic() {
		return null;
	}

	@Override
	public boolean getVictory() {
		return victory;
	}

}
