package Jolka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;







public class JolkaProblem extends Problem<String> {
	
	private ArrayList<JVariable> variables;
	private ArrayList<EqualConstraint> constraints;
	private static int CON = 0;
	
	public JolkaProblem(ArrayList<String> lines, ArrayList<String> words) {
		
		variables = new ArrayList<JVariable>();
		constraints = new ArrayList<EqualConstraint>();
		
		String line;
		String word;
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
		
	}
	
	public ArrayList<JVariable> solve() {
	
		JVariable var = chooseVariableMC();
		
		
		if(var == null) {

	
			
			solutionCount++;
			if(!firstSolutionReached) {
				firstSolutionReached = true;
				firstSolutionTime = System.currentTimeMillis();
				firstSolutionNodesCount = totalNodesCount;
				firstSolutionBacktracksCount = totalBacktracksCount;
			}
			
			return variables;
		}
		else {
			String varValueBefore = var.getValue();
			for(int i = 0; i<var.getDomain().size(); i++) {
				boolean flag = true;
				for(JVariable v : variables) {
					if(var.getDomain().get(i).equals(v.getValue())) flag = false;
				}
				if(flag) 
					var.setValue(var.getDomain().get(i));
				else {
					continue;
				}
			
				if(checkAllConstraints()) {
					totalNodesCount++;
					solve();
				}
				else {
					totalBacktracksCount++;
				}
				
			}
			var.setValue(varValueBefore);
		}
		
		totalBacktracksCount++;
		return variables;
	}
	
	
	public ArrayList<JVariable> solveFC() {
		
		JVariable var = chooseVariableMC();
		
		if(var == null) {

			
			
			solutionCount++;
			if(!firstSolutionReached) {
				firstSolutionReached = true;
				firstSolutionTime = System.currentTimeMillis();
				firstSolutionNodesCount = totalNodesCount;
				firstSolutionBacktracksCount = totalBacktracksCount;
			}
			
			return variables;
		}
		else {
			String varValueBefore = var.getValue();
			ArrayList<ArrayList<String>> saveDomains = new ArrayList<ArrayList<String>>();
			for(JVariable v : variables) {
				saveDomains.add((ArrayList<String>)v.getDomain().clone());
			}
			
			
			
			
			for(int i = 0; i<var.getDomain().size(); i++) {
				String chosenValue = var.getDomain().get(i);
				boolean uniqueFlag = true;
				for(JVariable v : variables) {
					if(chosenValue.equals(v.getValue())) uniqueFlag = false;
				}
				if(uniqueFlag) 
					var.setValue(chosenValue);
				else {
					continue;
				}
				
				boolean isDomainEmpty = false;
				for(EqualConstraint c : constraints) {
					if(c.getVariables().contains(var)) {
						if(!c.filterDomain(var)) {
							isDomainEmpty = true;
						}
						
					}
				}
			

				if(!isDomainEmpty && checkAllConstraints()) {
					totalNodesCount++;
					solveFC();
				}
				else {
					totalBacktracksCount++;
				}
				
				int ii = 0;
				for(JVariable v : variables) {
					
					v.setDomain((ArrayList<String>)saveDomains.get(ii).clone());
					ii++;
				}	
			}
			var.setValue(varValueBefore);
		}
		
		totalBacktracksCount++;
		return variables;
	}	
	
	
	public void orderDomainNatural() {
		return;
	}
	
	public void orderDomainRandom() {
		for(JVariable var : variables)
			Collections.shuffle(var.getDomain());
	}
	
	
	

	public boolean checkAllConstraints() {

		for(EqualConstraint constraint: constraints) {
			
			if(!constraint.check()) {
				return false;
			}
	
		}
		return true;
	}
	
	public JVariable chooseVariableNatural() {
		
		for(int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getValue() == null) return variables.get(i);
		}
		return null;
	}
	
	public JVariable chooseVariableMC() {
		int minDomain = Integer.MAX_VALUE;
		JVariable min = null;
		for(int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getDomain().size() < minDomain && variables.get(i).getValue() == null) {
				minDomain = variables.get(i).getDomain().size();
				min = variables.get(i);
			}
		}
		return min; 
	}
	
	public void show() {
		System.out.println("\nACROSS\n");
		for(int i = 0; i < variables.size(); i++) {
			if(variables.get(i).getDirection() == Direction.ACROSS && variables.get(i).getValue() != null) System.out.println(variables.get(i).getValue());
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Loader l = new Loader();
		int index = 3;
		String puzzle = "Jolka/puzzle"+index;
		String words = "Jolka/words"+index;
		JolkaProblem a = new JolkaProblem(l.loadPuzzle(puzzle),l.loadWords(words));
		
		
		a.test();
		a.testFC();
		
	}
	
	
	

}
