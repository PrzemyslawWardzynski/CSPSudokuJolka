package Sudoku;

import java.util.ArrayList;
import java.util.Collections;

import Jolka.JVariable;







public class SudokuProblem extends Problem<Integer> {

	public static int SIZE = 9;
	public static int SQUARE_SIZE = 3;
	SudokuVariable[][] variables;
	ArrayList<SudokuConstraint> constraints;
	
	
	public SudokuProblem(int [][]data) {
		
		ArrayList<SudokuVariable> constraintVariables;
		variables = new SudokuVariable[SIZE][SIZE];
		constraints = new ArrayList<SudokuConstraint>();
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				int cellValue = data[i][j];
				variables[i][j] = new SudokuVariable(cellValue,null);
			}
		}
		//POZIOME
		for(int i = 0; i < SIZE; i++) {
			constraintVariables = new ArrayList<SudokuVariable>();
			for(int j = 0; j < SIZE; j++) {
				constraintVariables.add(variables[i][j]);
			}
			constraints.add(new SudokuConstraint(constraintVariables));
		}
		//PIONOWE
		for(int i = 0; i < SIZE; i++) {
			constraintVariables = new ArrayList<SudokuVariable>();
			for(int j = 0; j < SIZE; j++) {
				constraintVariables.add(variables[j][i]);
			}
			constraints.add(new SudokuConstraint(constraintVariables));
		}
		
		//KWADRAT
		for(int i = 0; i < SIZE; i+=SQUARE_SIZE) {
			
			for(int j = 0; j < SIZE; j+=SQUARE_SIZE) {
				constraintVariables = new ArrayList<SudokuVariable>();
				constraintVariables.add(variables[i][j]);
				constraintVariables.add(variables[i][j+1]);
				constraintVariables.add(variables[i][j+2]);
				constraintVariables.add(variables[i+1][j]);
				constraintVariables.add(variables[i+1][j+1]);
				constraintVariables.add(variables[i+1][j+2]);
				constraintVariables.add(variables[i+2][j]);
				constraintVariables.add(variables[i+2][j+1]);
				constraintVariables.add(variables[i+2][j+2]);
				constraints.add(new SudokuConstraint(constraintVariables));
			}
			
		}
		
	}
	
	public void show() {
		 for(int i = 0; i < SIZE; i ++) {
	    	  for(int j = 0; j < SIZE; j++) {
	    		  System.out.print(variables[i][j].getValue()+" ");
	    	  }
	    	  System.out.println();
	      }
	}
	
	public boolean checkAllConstraints() {
		int i = 0;
		for(SudokuConstraint constraint: constraints) {
			
			if(!constraint.check()) {
				return false;
			}
			i++;
		}
		return true;
	}
	@Override
	public SudokuVariable[][] solve() {
		
		SudokuVariable var = chooseVariableMC();
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
			int varValueBefore = var.getValue();
			for(int i = 0; i<var.getDomain().size(); i++) {
				
				var.setValue(var.getDomain().get(i));
				
				
				if(checkAllConstraints()) {
					solve();
					totalNodesCount++;
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
	@Override
	public SudokuVariable[][] solveFC() {
	
		SudokuVariable var = chooseVariableMC();
		
		
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
			int varValueBefore = var.getValue();
			ArrayList<ArrayList<Integer>> saveDomains = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < variables.length; i++) {
				for(int j = 0; j < variables.length; j++) {
					saveDomains.add((ArrayList<Integer>)variables[i][j].getDomain().clone());					
				}
			}
			
			for(int i = 0; i<var.getDomain().size(); i++) {

				int chosenValue = var.getDomain().get(i);
				
				var.setValue(chosenValue);
				
				
				boolean isDomainEmpty = false;
				for(SudokuConstraint c : constraints) {
					if(c.getVariables().contains(var)) {
						if(!c.filterDomain(var)) {
							isDomainEmpty = true;
						}
						
					}
				}
			

				if(!isDomainEmpty && checkAllConstraints()) {
					solveFC();
					totalNodesCount++;
				}
				else {
					totalBacktracksCount++;
				}
				
				int ii = 0;
				for(int k = 0; k < variables.length; k++) {
					for(int j = 0; j < variables.length; j++) {
						variables[k][j].setDomain((ArrayList<Integer>)saveDomains.get(ii).clone());		
						ii++;
					}
				}
	
			}
			var.setValue(varValueBefore);
		}
		
		totalBacktracksCount++;
		return variables;
	}
	
	
	
	
	
	
	
	
	public SudokuVariable chooseVariableNatural() {
		
		for(int i = 0; i < variables.length; i++) {
			for(int j = 0; j < variables.length; j++) {
				if(variables[i][j].getValue() == 0) return variables[i][j];
			}
		}
		return null;	
	}
	
	public SudokuVariable chooseVariableMC() {
		int minDomain = Integer.MAX_VALUE;
		SudokuVariable min = null;
		for(int i = 0; i < variables.length; i++) {
			for(int j = 0; j < variables.length; j++) {
				if(variables[i][j].getDomain().size() < minDomain && variables[i][j].getValue() == 0) {
					minDomain = variables[i][j].getDomain().size();
					min = variables[i][j];
				}
			}
		}
		return min; 
	}

	public void orderDomainRandom() {
		for(int i = 0; i < variables.length; i++) {
			for(int j = 0; j < variables.length; j++) {
				Collections.shuffle(variables[i][j].getDomain());
			}
		}

	}
	
	
	
	
	public static void main(String[] args) {
		
		Loader a = new Loader();
		int [][] data = a.loadData("Sudoku/Sudoku.csv", 6);
		SudokuProblem p = new SudokuProblem(data);
		p.test();
		p.testFC();
		
	}

}
