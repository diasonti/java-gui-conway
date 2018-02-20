package game;

import java.util.Arrays;

import gui.ConwayJFrame;

/**
 * Created by Vladimir Danilov on 20/02/2018 : 22:21.
 */
public class Game {
	
	// Can be changed
	public static final int FIELD_SIZE = 40;
	
	private static ConwayJFrame frame;
	private static boolean[][] field;
	private static byte[][] nCount;
	
	private static int gen = 0;
	private static boolean alive = false;
	
	public static void main(String[] args) {
		setup();
	}
	
	private static void setup(){
		field = new boolean[FIELD_SIZE][FIELD_SIZE];
		nCount = new byte[FIELD_SIZE][FIELD_SIZE];
		frame = new ConwayJFrame();
	}
	
	public static void loadDemoField(){
		if(FIELD_SIZE != 40)
			return;
		FileReader fr = new FileReader("DemoGame.dat");
		field = fr.read(40);
	}
	
	private static void debugField(){
		field[0][0] = true;
		field[0][1] = true;
		field[1][0] = true;
		field[FIELD_SIZE - 1][FIELD_SIZE - 1] = true;
		field[FIELD_SIZE - 1][FIELD_SIZE - 2] = true;
		field[FIELD_SIZE - 2][FIELD_SIZE - 1] = true;
		field[5][5] = true;
		field[5][6] = true;
		field[6][6] = true;
		field[5][7] = true;
		field[4][5] = true;
	}
	
	public synchronized static void generation(){
		refreshNCount();
		for(int i = 0; i < FIELD_SIZE; i++){
			for(int j = 0; j < FIELD_SIZE; j++){
				decideChange(i, j);
			}
		}
		gen++;
	}
	
	private static void decideChange(final int row, final int col){
		switch(nCount[row][col]){
			case 0:
			case 1: // death
				if(field[row][col]){
					field[row][col] = false;
				}
				break;
			case 2:
				break;
			case 3: // birth
				if(!field[row][col]){
					field[row][col] = true;
				}
				break;
			default: // death
				if(field[row][col]){
					field[row][col] = false;
				}
		}
	}
	
	private static void refreshNCount(){
		clearNCount();
		for(int i = 0; i < FIELD_SIZE; i++){
			for(int j = 0; j < FIELD_SIZE; j++){
				if(field[i][j]){
					for(int k = i - 1; k <= i + 1; k++){
						for(int l = j - 1; l <= j + 1; l++){
							if((k != i || l != j) && exists(k, l)){
								nCount[k][l]++;
							}
						}
					}
				}
			}
		}
	}
	private static boolean exists(final int row, final int col){
		return (row >= 0 && col >= 0 && row < FIELD_SIZE && col < FIELD_SIZE);
	}
	
	private static void clearNCount(){
		for(byte[] row : nCount){
			Arrays.fill(row, (byte)0);
		}
	}
	
	public synchronized static boolean isExtinct(){
		for(boolean[] row : field){
			for(boolean cell : row){
				if(cell){
					alive = true;
					return !alive;
				}
			}
		}
		alive = false;
		return !alive;
	}
	
	public synchronized static void printField(){
		System.out.println();
		for(boolean[] row : field){
			for(boolean cell : row){
				System.out.print(cell ? "+" : "-");
			}
			System.out.println();
		}
	}
	
	public synchronized static void printNCount(){
		System.out.println();
		for(byte[] row : nCount){
			for(byte cell : row){
				System.out.print(cell);
			}
			System.out.println();
		}
	}
	
	public static boolean[][] getField(){
		return field;
	}
	
	public static boolean isCellAlive(final int row, final int col){
		if(exists(row, col)) {
			return field[row][col];
		}
		return false;
	}
	
	public static void setCell(final int row, final int col, final boolean isAlive){
		if(exists(row, col)) {
			field[row][col] = isAlive;
		}
	}
	
	public static int getGen() {
		return gen;
	}
}
