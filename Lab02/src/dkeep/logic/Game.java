package dkeep.logic;

public class Game {
	private Map map;
	private Logic logic;
	
	
	public Game(Map startingMap, Logic startingLogic) {
		map = startingMap;
		logic = startingLogic;
	}
	
	public boolean isGameOver() {
		return logic.gameOver();
	}
	
	public char[][] getGameMap() {
		return map.getMap();
	}
	
	public void print(){
		char[][] m = map.getMap();
		System.out.println(m);
		m[logic.hero.getX()][logic.hero.getY()] = logic.hero.symbol;
		for(int i = 0; i < logic.enemy.length/*size()*/; i++){
			m[logic.enemy[i]/*.get(i)*/.getX()][logic.enemy[i]/*.get(i)*/.getY()] = logic.enemy[i]/*.get(i)*/.symbol;
		}
		for(int i = 0; i < m.length; i++){
			System.out.println(m[i]);
		}
	}
	
	public void update(char dir) {
		logic.movement(dir, map);
	}
	
}
