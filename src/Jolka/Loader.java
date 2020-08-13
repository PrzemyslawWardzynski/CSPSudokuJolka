package Jolka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;



public class Loader {

	public ArrayList<String> loadData(String fileNamePuzzle, String fileNameWords) {
		
		
		try {
			  ArrayList<JVariable> variables = new ArrayList<JVariable>();
			  ArrayList<EqualConstraint> constraints = new ArrayList<EqualConstraint>();
			  ArrayList<String> lines = new ArrayList<String>();
			  ArrayList<String> words = new ArrayList<String>();
		      File file = new File(fileNamePuzzle);
		      BufferedReader reader = new BufferedReader(new FileReader(file));
		      String line = null;
		      File wordsFile = new File(fileNameWords);
		      BufferedReader wordsReader = new BufferedReader(new FileReader(wordsFile));
		      String word = null;
		      while((word = wordsReader.readLine()) != null) {
		    	  words.add(word);
		      }
		   
		      while((line = reader.readLine()) != null) {
		    	  lines.add(line);
		      }

		      
		      for(int i = 0; i < lines.size(); i++) {
		    	  line = lines.get(i);
		    	  for(int j = 0; j < line.length(); j++) {
		    		  if(line.charAt(j) != '#') {
		    			  
		    			  int length = 0;
		    			  while(j < line.length() && line.charAt(j) != '#' ) {
		    				  length++;
		    				  j++;
		    			  }
		    			  
		    			  ArrayList<String> domain = new ArrayList<String>();
		    			  for(int k = 0; k < words.size(); k++) {
		    				  word = words.get(k);
		    				  if(word.length() == length) domain.add(word);
		    			  }

		    			  if(length > 1) variables.add(new JVariable(null,domain,i,j-length,length,Direction.ACROSS));

		    		  }
		    	  }
		      }
		      
		      for(int i = 0; i < lines.get(0).length(); i++) {
		    
		    	  for(int j = 0; j < lines.size(); j++) {
		    		  line = lines.get(j);
		    		  if(line.charAt(i) != '#') {
		    			  
		    			  int length = 0;
		    			  while(j < lines.size() && (line = lines.get(j)).charAt(i) != '#') {
		    				  length++;
		    				  j++;

		    			  }
		    			  ArrayList<String> domain = new ArrayList<String>();
		    			  for(int k = 0; k < words.size(); k++) {
		    				  word = words.get(k);
		    				  if(word.length() == length) domain.add(word);
		    			  }

		    			  if(length > 1) variables.add(new JVariable(null,domain,j-length,i,length,Direction.DOWN));
		    			  
		    		  }
		    	  }
		      }
		      
		      for(int i = 0; i < lines.size(); i++) {
		    	  for(int j = 0; j < lines.get(0).length(); j++) {
		    		  ArrayList<JVariable> constraintVariables = new ArrayList<JVariable>();
		    		  for(JVariable v : variables) {
		    			  
		    			 if(v.inRange(i, j)) {
		    	
		    				 constraintVariables.add(v);
		    			 }
		    		  }
		    		  if(constraintVariables.size() == 2) {
		    			  int firstWordPosition = j - constraintVariables.get(0).getStartY();
		    			  int secondWordPosition = i - constraintVariables.get(1).getStartX();
		    			  constraints.add(new EqualConstraint(constraintVariables,firstWordPosition,secondWordPosition));
		 
		    		  }
	  
		    	  }
		      }

		      reader.close();
		      wordsReader.close();
	
		    }
			catch(Exception ex) {
		      ex.printStackTrace();
		    }
			return null;
	}
	
	public ArrayList<String> loadWords(String fileNameWords) {
		
		try {
			  ArrayList<String> words = new ArrayList<String>();
		      File wordsFile = new File(fileNameWords);
		      BufferedReader wordsReader = new BufferedReader(new FileReader(wordsFile));
		      String word = null;
		      
		      while((word = wordsReader.readLine()) != null) {
		    	  words.add(word);
		      }
		      wordsReader.close();
		      return words;
		
		    }
			catch(Exception ex) {
		      ex.printStackTrace();
		    }
			return null;
	}

	
	public ArrayList<String> loadPuzzle(String fileNamePuzzle) {
		
		try {
			  ArrayList<String> lines = new ArrayList<String>();
		      File file = new File(fileNamePuzzle);
		      BufferedReader reader = new BufferedReader(new FileReader(file));
		      String line = null;
		     
		      while((line = reader.readLine()) != null) {
		    	  lines.add(line);
		      }
		      reader.close();
		
		      return lines;
		    }
			catch(Exception ex) {
		      ex.printStackTrace();
		    }
			return null;
	}
	
	
	public static void main(String[] args) {
		Loader a = new Loader();
		a.loadWords("Jolka/words1");
		a.loadPuzzle("Jolka/puzzle1");
	}

}
