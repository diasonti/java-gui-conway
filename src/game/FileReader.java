package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Vladimir Danilov on 21/02/2018 : 00:49.
 */
public class FileReader {
	
	private final char ALIVE = '+';
	private String fileName;
	
	public FileReader(String fileName){
		this.fileName = fileName;
	}
	
	public boolean[][] read(final int size){
		
		try {
			Scanner s = new Scanner(new File(fileName));
			boolean[][] data = new boolean[size][size];
			int row = 0;
			while(s.hasNextLine()){
				String line = s.nextLine();
				if(line.length() != size){
					return null;
				}
				for(int i = 0; i < size; i++){
					data[row][i] = (line.charAt(i) == ALIVE);
				}
				row++;
			}
			if(row != size){
				return null;
			}
			return data;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
