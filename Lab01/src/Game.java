import java.util.*;

public class Game {
	public static void print(char[][] table){
		for(int i = 0; i < table.length; i++){
			System.out.println(table[i]);
		}
	}

	public static void openDoors(char[][] table){
		for(int i = 0; i < table.length; i++){
			for(int j = 0; j < table[i].length; j++){
				if(table[i][j] == 'I')
					table[i][j] = 'S';
			}
		}
	}

	public static int left(char[][] table, int[] hero){
		if(table[hero[0]][hero[1]-1] == 'X'){
			return 0;
		}
		else if(table[hero[0]][hero[1]-1] == 'S'){
			return 1;
		}
		else if(table[hero[0]][hero[1]-1] == ' '){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]][hero[1]-1] = 'H';
			hero[1] -= 1;
			return 0;
		}
		else if(table[hero[0]][hero[1]-1] == 'k'){
			openDoors(table);
			return 0;
		}
		return 0;
	}
	public static int up(char [][] table, int[] hero){
		if(table[hero[0]-1][hero[1]] == 'X'){
			return 0;
		}
		else if(hero[0] > 1 && table[hero[0]-2][hero[1]] == 'G'){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]-1][hero[1]] = 'H';
			hero[0] -=1;
			return -1;
		}
		else if(table[hero[0]-1][hero[1]+1] == 'G'){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]-1][hero[1]] = 'H';
			hero[0]-=1;
			return -1;
		}
		else if(table[hero[0]-1][hero[1]] == ' '){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]-1][hero[1]] = 'H';
			hero[0] -= 1;
			return 0;
		}
		return 0;
	}
	public static int down(char [][] table, int[] hero){
		if(table[hero[0]+1][hero[1]] == 'X'){
			return 0;
		}
		else if(table[hero[0]+1][hero[1]] == ' '){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]+1][hero[1]] = 'H';
			hero[0] += 1;
			return 0;
		}
		return 0;
	}

	public static int right(char[][] table, int[] hero){
		if(table[hero[0]][hero[1]+1] == 'X' || table[hero[0]][hero[1]+1] == 'I'){
			return 0;
		}
		else if (table[hero[0]][hero[1]+1] == ' '){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]][hero[1]+1] = 'H';
			hero[1] += 1;
			return 0;
		}
		else if(table[hero[0]][hero[1]+1] == 'S'){
			return 1;
		}
		else if(table[hero[0]-1][hero[1]+1] == 'G'){
			table[hero[0]][hero[1]] = ' ';
			table[hero[0]][hero[1]+1] = 'H';
			hero[0] += 1;
			return -1;
		}
		return 0;
	}

	public static int movement(char[][] table, int[] hero) {
		Scanner scan = new Scanner(System.in);
		if (scan.hasNext("w")) {
			if(up(table, hero) == -1){
				print(table);
				System.out.println("Game Over");
				return -1;
			}
			print(table);
		}
		if(scan.hasNext("a")){
			if(left(table,hero) == 1){
				System.out.println("Victory!");
				return -1;
			}
			print(table);
	}
		if(scan.hasNext("d")){
			int var = right(table,hero);
			if(var == -1){
				print(table);
				System.out.println("Game Over");
				return -1;
			}
			else if(var == 1){
				System.out.println("Victory!");
				return -1;
			}
			print(table);
		}
		if(scan.hasNext("s")){
			down(table,hero);
			print(table);
		}	
		return 0;
	}

	public static void main(String[] args){
		char[][] table = {{'X','X','X','X','X','X','X','X','X','X'},{'X','H',' ',' ','I',' ','X',' ','G','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
		int[] hero = {1,1};
		print(table);
		while(movement(table,hero) != -1){
	
		}
		return;
	} 
}