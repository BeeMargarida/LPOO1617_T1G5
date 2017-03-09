import java.util.*;
 
public class Game {
	private static boolean key = false;
	private static char symbol = 'H';
	
	public static void print(char[][] table){
        for(int i = 0; i <  table.length; i++){
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
    public static void movGuard(char[][] table, int[] guard, char[] perc, int iter){
        if(iter == perc.length)
            iter = 0;
        if(perc[iter] == 'a'){
            table[guard[0]][guard[1]] = ' ';
            table[guard[0]][guard[1]-1] = 'G';
            guard[1] -= 1;
        }
        else if(perc[iter] == 's'){
            table[guard[0]][guard[1]] = ' ';
            table[guard[0]+1][guard[1]] = 'G';
            guard[0] += 1;
        }
        else if(perc[iter] == 'd'){
            table[guard[0]][guard[1]] = ' ';
            table[guard[0]][guard[1]+1] = 'G';
            guard[1] += 1;
        }
        else if(perc[iter] == 'w'){
            table[guard[0]][guard[1]] = ' ';
            table[guard[0]-1][guard[1]] = 'G';
            guard[0] -= 1;
        }
        iter++;
    }
    
    public static int left(char[][] table, int[] hero, int level){
        if(table[hero[0]][hero[1]-1] == 'X'){
            return 0;
        }
        else if(table[hero[0]][hero[1]-1] == 'S'){
            return 1;
        }
        else if(table[hero[0]][hero[1]-1] == ' '){
            table[hero[0]][hero[1]] = ' ';
            table[hero[0]][hero[1]-1] = symbol;
            hero[1] -= 1;
            return 0;
        }
        else if(table[hero[0]][hero[1]-1] == 'k'){
        	if(level == 2){
        		table[hero[0]][hero[1]] = ' ';
        		symbol = 'K';
        		table[hero[0]][hero[1]-1] = symbol;
        		hero[1] -= 1;
        		key = true;
        	}
        	else
        		openDoors(table);
            return 0;
        }
        else if(table[hero[0]][hero[1]-1] == 'I'){
        	if(level == 2){
        		if(key)
        			openDoors(table);
        	}
        }
        return 0;
    }
    
    public static int up(char [][] table, int[] hero, int level){
        if(table[hero[0]-1][hero[1]] == 'X'){
            return 0;
        }
        else if(table[hero[0]-1][hero[1]] == 'S'){
            return 1;
        }
        else if(table[hero[0]-1][hero[1]] == ' '){
            table[hero[0]][hero[1]] = ' ';
            table[hero[0]-1][hero[1]] = symbol;
            hero[0] -= 1;
            return 0;
        }
        else if(table[hero[0]-1][hero[1]] == 'k'){
        	if(level == 2){
        		table[hero[0]][hero[1]] = ' ';
        		symbol = 'K';
        		table[hero[0]-1][hero[1]] = symbol;
        		hero[0] -= 1;
        		key = true;
        	}
        	else
        		openDoors(table);
            return 0;
        }
        else if(table[hero[0]-1][hero[1]] == 'I'){
        	if(level == 2){
        		if(key)
        			openDoors(table);
        	}
        }
        return 0;
    }
    
    public static int down(char [][] table, int[] hero, int level){
        if(table[hero[0]+1][hero[1]] == 'X'){
            return 0;
        }
        else if(table[hero[0]+1][hero[1]] == 'S'){
            return 1;
        }
        else if(table[hero[0]+1][hero[1]] == ' '){
            table[hero[0]][hero[1]] = ' ';
            table[hero[0]+1][hero[1]] = symbol;
            hero[0] += 1;
            return 0;
        }
        else if(table[hero[0]+1][hero[1]] == 'k'){
        	if(level == 2){
        		table[hero[0]][hero[1]] = ' ';
        		symbol = 'K';
        		table[hero[0]+1][hero[1]] = symbol;
        		hero[0] += 1;
        		key = true;
        	}
        	else
        		openDoors(table);
            return 0;
        }
        else if(table[hero[0]+1][hero[1]] == 'I'){
        	if(level == 2){
        		if(key)
        			openDoors(table);
        	}
        }
        return 0;
    }
 
    public static int right(char[][] table, int[] hero, int level){
        if(table[hero[0]][hero[1]+1] == 'X' || table[hero[0]][hero[1]+1] == 'I'){
            return 0;
        }
        else if (table[hero[0]][hero[1]+1] == ' '){
            table[hero[0]][hero[1]] = ' ';
            table[hero[0]][hero[1]+1] = symbol;
            hero[1] += 1;
            return 0;
        }
        else if(table[hero[0]][hero[1]+1] == 'S'){
            return 1;
        }
        else if(table[hero[0]][hero[1]+1] == 'k'){
        	if(level == 2){
        		table[hero[0]][hero[1]] = ' ';
        		symbol = 'K';
        		table[hero[0]][hero[1]+1] = symbol;
        		hero[1] += 1;
        		key = true;
        	}
        	else
        		openDoors(table);
            return 0;
        }
        else if(table[hero[0]][hero[1]+1] == 'I'){
        	if(level == 2){
        		if(key)
        			openDoors(table);
        	}
        }
        return 0;
    }
 
    public static int movement(char[][] table, int[] hero, int[] guard, char[] perc, int iter) {
    	movGuard(table,guard,perc,iter);
        Scanner scan = new Scanner(System.in);
        if (scan.hasNext("w")) {
            if(up(table, hero,1) == 1){
                //print(table);
                System.out.println("Victory");
                return -1;
            }
            print(table);
        }
        if(scan.hasNext("a")){
            if(left(table,hero,1) == 1){
                System.out.println("Victory!");
                return -1;
            }
            print(table);
        }
        if(scan.hasNext("d")){
        	/*
            int var = right(table,hero);
            if(var == -1){
                print(table);
                System.out.println("Game Over");
                return -1;
            }
            */
            if(right(table,hero,1) == 1){
            	System.out.println("Victory!");
            	return -1;
            }
            print(table);
        }
        if(scan.hasNext("s")){
            if(down(table,hero,1) == 1){
            	System.out.println("Victory!");
               	return -1;
            }
            print(table);
        }  
        return 0;
    }
    
    public static boolean caught(int[] hero, int[] guard){
    	return ((hero[0] == guard[0] && hero[1] == guard[1]) || (hero[0]+1 == guard[0] && hero[1] == guard [1]) || (hero[0]-1 == guard[0] && hero[1] == guard [1]) || (hero[1]+1 == guard[1] && hero[0] == guard [0]) || (hero[1]-1 == guard[1] && hero[0] == guard [0]));
    }
 
    public static int stage_1(){
    	 char[][] table = {{'X','X','X','X','X','X','X','X','X','X'},{'X','H',' ',' ','I',' ','X',' ','G','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'X',' ','I',' ','I',' ','X',' ',' ','X'},{'X','X','X',' ','X','X','X',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},{'X','X','X',' ','X','X','X','X',' ','X'},{'X',' ','I',' ','I',' ','X','k',' ','X'},{'X','X','X','X','X','X','X','X','X','X'}};
         int[] hero = {1,1};
         int[] guard = {1,8};
         int iter = 0;
         char[] perc={'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
         print(table);
         while(movement(table,hero,guard,perc, iter) != -1){
         	if(caught(hero, guard)){
         		//print(table);
                 System.out.println("Game Over");
                 return 1;
         	}
         	iter++;
         	if(iter == perc.length)
         		iter = 0;
         }
         return 0;
    }
    
    public static void movOgre(char[][] table, int[] ogre){
    	java.util.Random rng = new java.util.Random();
    	int mov = rng.nextInt() % 4;
    	if(mov < 0)
    		mov *= -1;
    	if(mov == 0){
    		//move up
    		if(table[ogre[0]-1][ogre[1]] == 'X'|| table[ogre[0]-1][ogre[1]] == 'S'){
                return;
            }
            else if(table[ogre[0]-1][ogre[1]] == ' '){
            	if(table[ogre[0]][ogre[1]] == '$')
            		table[ogre[0]][ogre[1]] = 'k';
            	else
            		table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]-1][ogre[1]] = 'O';
                ogre[0] -= 1;
                return;
            }
            else if(table[ogre[0]-1][ogre[1]] == 'k'){
            	table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]-1][ogre[1]] = '$';
                ogre[0] -= 1;
                return;
            }
    	}
    	
    	else if(mov == 1){
    		//left(table,ogre);
    		if(table[ogre[0]][ogre[1]-1] == 'X'|| table[ogre[0]-1][ogre[1]-1] == 'S'){
                return;
            }
            else if(table[ogre[0]][ogre[1]-1] == ' '){
            	if(table[ogre[0]][ogre[1]] == '$')
            		table[ogre[0]][ogre[1]] = 'k';
            	else
            		table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]][ogre[1]-1] = 'O';
                ogre[1] -= 1;
                return;
            }
            else if(table[ogre[0]][ogre[1]-1] == 'k'){
            	table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]][ogre[1]-1] = '$';
                ogre[1] -= 1;
                return;
            }
    	}
    	
    	else if(mov == 2){
    		//right(table,ogre);
    		if(table[ogre[0]][ogre[1]+1] == 'X'|| table[ogre[0]][ogre[1]+1] == 'S'){
                return;
            }
            else if(table[ogre[0]][ogre[1]+1] == ' '){
            	if(table[ogre[0]][ogre[1]] == '$')
            		table[ogre[0]][ogre[1]] = 'k';
            	else
            		table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]][ogre[1]+1] = 'O';
                ogre[1] += 1;
                return;
            }
            else if(table[ogre[0]][ogre[1]+1] == 'k'){
            	table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]][ogre[1]+1] = '$';
                ogre[1] += 1;
                return;
            }
    	}
    	else if(mov == 3){
    		//down(table,ogre);
    		if(table[ogre[0]+1][ogre[1]] == 'X'|| table[ogre[0]+1][ogre[1]] == 'S'){
                return;
            }
            else if(table[ogre[0]+1][ogre[1]] == ' '){
            	if(table[ogre[0]][ogre[1]] == '$')
            		table[ogre[0]][ogre[1]] = 'k';
            	else
            		table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]+1][ogre[1]] = 'O';
                ogre[0] += 1;
                return;
            }
            else if(table[ogre[0]+1][ogre[1]] == 'k'){
            	table[ogre[0]][ogre[1]] = ' ';
                table[ogre[0]+1][ogre[1]] = '$';
                ogre[0] += 1;
                return;
            }
    	}
    }
    
    public static void attackOgre(char[][] table, int[] ogre, int[] club){
    	java.util.Random rng = new java.util.Random();
    	int mov = rng.nextInt() % 4;
    	if(mov < 0)
    		mov *= -1;
    	
    	if(table[club[0]][club[1]] == '$')
    		table[club[0]][club[1]] = 'k';
    	else if(table[club[0]][club[1]] == '*')
    		table[club[0]][club[1]] = ' ';
    	
    	if(mov == 0){
    		//move up
    		if(table[ogre[0]-1][ogre[1]] == 'X'|| table[ogre[0]-1][ogre[1]] == 'S'){
    			club[0] = 0;
            	club[1] = 0;
                return;
            }
            else if(table[ogre[0]-1][ogre[1]] == ' '){
            	table[ogre[0]-1][ogre[1]] = '*';
            	club[0] = ogre[0]-1;
            	club[1] = ogre[1];
            	return;
            }
            else if(table[ogre[0]-1][ogre[1]] == 'k'){
                table[ogre[0]-1][ogre[1]] = '$';
                club[0] = ogre[0]-1;
            	club[1] = ogre[1];
                return;
            }
    	}
    	
    	else if(mov == 1){
    		//left(table,ogre);
    		if(table[ogre[0]][ogre[1]-1] == 'X'|| table[ogre[0]-1][ogre[1]-1] == 'S'){
    			club[0] = 0;
            	club[1] = 0;
                return;
            }
            else if(table[ogre[0]][ogre[1]-1] == ' '){
            	table[ogre[0]][ogre[1]-1] = '*';
            	club[0] = ogre[0];
            	club[1] = ogre[1]-1;
            	return;
            }
            else if(table[ogre[0]][ogre[1]-1] == 'k'){
                table[ogre[0]][ogre[1]-1] = '$';
                club[0] = ogre[0];
            	club[1] = ogre[1]-1;
                return;
            }
    	}
    	
    	else if(mov == 2){
    		//right(table,ogre);
    		if(table[ogre[0]][ogre[1]+1] == 'X'|| table[ogre[0]][ogre[1]+1] == 'S'){
    			club[0] = 0;
            	club[1] = 0;
                return;
            }
            else if(table[ogre[0]][ogre[1]+1] == ' '){
            	table[ogre[0]][ogre[1]+1] = '*';
            	club[0] = ogre[0];
            	club[1] = ogre[1]+1;
                return;
            }
            else if(table[ogre[0]][ogre[1]+1] == 'k'){
                table[ogre[0]][ogre[1]+1] = '$';
            	club[0] = ogre[0];
            	club[1] = ogre[1]+1;
                return;
            }
    	}
    	else if(mov == 3){
    		//down(table,ogre);
    		if(table[ogre[0]+1][ogre[1]] == 'X'|| table[ogre[0]+1][ogre[1]] == 'S'){
    			club[0] = 0;
            	club[1] = 0;
                return;
            }
            else if(table[ogre[0]+1][ogre[1]] == ' '){
            	table[ogre[0]+1][ogre[1]] = '*';
            	club[0] = ogre[0]+1;
            	club[1] = ogre[1];
                return;
            }
            else if(table[ogre[0]+1][ogre[1]] == 'k'){
                table[ogre[0]+1][ogre[1]] = '$';
                club[0] = ogre[0]+1;
            	club[1] = ogre[1];
                return;
            }
    	}
	}
    
    public static int movement2(char[][] table, int[] hero, int[] ogre, int[] club, char symbol, boolean crazy, boolean key){
    	movOgre(table, ogre);
    	if(crazy)
    		attackOgre(table, ogre, club);
    	Scanner scan = new Scanner(System.in);
        if (scan.hasNext("w")) {
            if(up(table, hero,2) == 1){
                System.out.println("Victory");
                return -1;
            }
            print(table);
        }
        if(scan.hasNext("a")){
            if(left(table,hero,2) == 1){
                System.out.println("Victory!");
                return -1;
            }
            print(table);
        }
        if(scan.hasNext("d")){
            if(right(table,hero,2) == 1){
            	System.out.println("Victory!");
            	return -1;
            }
            print(table);
        }
        if(scan.hasNext("s")){
            if(down(table,hero,2) == 1){
            	System.out.println("Victory!");
               	return -1;
            }
            print(table);
        }  
        return 0;
    }
    
    
    public static int stage_2(){
    	char[][] table = {{'X','X','X','X','X','X','X','X','X'},{'I',' ',' ',' ','O',' ',' ','k','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X',' ',' ',' ',' ',' ',' ',' ','X'},{'X','H',' ',' ',' ',' ',' ',' ','X'},{'X','X','X','X','X','X','X','X','X'}};
    	int[] hero = {7,1};
    	key = false;
    	symbol = 'H';
    	int[] ogre = {1,4};
    	int[] club = {0,0};
    	boolean crazy = false;
    	print(table);
    	while(movement2(table,hero,ogre,club,symbol,crazy,key) != -1){
    		if(crazy){
    			if(caughtcrazy(hero,ogre,club)){
    				System.out.println("Game Over");
    				return 1;
    			}
    		}
    		else{
    			if(caught(hero,ogre)){
    				System.out.println("Game Over");
    				return 1;
    			}
    		}
    	}
    	return 0;
    }
    
    public static boolean caughtcrazy(int[] hero, int[] ogre, int[] club){
    	boolean tmp = caught(hero, ogre);
    	boolean tmp2 = false;
    	if(!(club[0] == 0 && club[1] == 0)){
    		System.out.println(club[0]);
    		System.out.println(club[1]);
    		tmp2 = (hero[0] == club[0] && hero[1] == club[1]) || (hero[0]+1 == club[0] && hero[1] == club[1]) || (hero[0]-1 == club[0] && hero[1] == club [1]) || (hero[1]+1 == club[1] && hero[0] == club[0]) || (hero[1]-1 == club[1] && hero[0] == club[0]);
    	}
    	return (tmp || tmp2);
    }
    
    public static void main(String[] args){
       if(stage_1() == 0){
    	   System.out.println("\n\n\nPrepare yourself for the KEEEEEEEEEEEEEEEEEPP!");
    	   System.out.println("MUAHAHAHAHAHAHA!\n");
    	   stage_2();
       }
       return;
    }
} 