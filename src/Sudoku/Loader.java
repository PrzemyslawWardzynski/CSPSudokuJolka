package Sudoku;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Loader {

	public int[][] loadData(String fileName, int puzzleID) {
		
	
		try {
			  boolean reachedPuzzleData = false;
		      File file = new File(fileName);
		      BufferedReader reader = new BufferedReader(new FileReader(file));
		      String line = null;
		      String [] lineSplit = null;
		      
		      while(!reachedPuzzleData) {
		    	  line = reader.readLine();
		    	  lineSplit = line.split(";");
		    	  if(lineSplit[0].equals(Integer.toString(puzzleID))) {
		    		  reachedPuzzleData = true;
		    	  }
		      	}
		      int [][] tab = new int[9][9];
		      int k = 0;
		      String data = lineSplit[2];
		 
	
		      for(int i = 0; i < 9; i ++) {
		    	  for(int j = 0; j < 9; j++) {
		    		  if(data.charAt(k) != '.') {
		    			  tab[i][j] = Integer.parseInt(String.valueOf(data.charAt(k)));
		    			  
		    			  
		    		  }
	
		    		  k++;
		    	  }

		      }
		      
		      
		      reader.close();
		      return tab;
		    }
			catch(Exception ex) {
		      ex.printStackTrace();
		    }
			return null;
	}
	
	
	
	public static void main(String[] args) {
		Loader a = new Loader();
		a.loadData("Sudoku/Sudoku.csv", 1);
	}

}
