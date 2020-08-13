package Sudoku;
import java.util.ArrayList;

import Jolka.Direction;
import Jolka.JVariable;



public class SudokuConstraint extends Constraint<Integer> {

	public static int SIZE = 9;
	protected ArrayList<SudokuVariable> variables;
	
	public SudokuConstraint(ArrayList<SudokuVariable> variables) {
		this.variables = variables;
	}
	
	public ArrayList<SudokuVariable> getVariables() {
		return variables;
	}
	
	public boolean check() {
		int[] count = new int[SIZE];
		for(SudokuVariable variable : variables) {
			if(variable.getValue() != 0) {
				int value = variable.getValue()-1;
				count[value]++;
				if(count[value] > 1) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean filterDomain(Variable<Integer> var) {
		var = (SudokuVariable)var;
		for(SudokuVariable v : getVariables()) {
			if(!v.equals(var)) {
				ArrayList<Integer> domain = v.getDomain();
				domain.remove(var.value);
			}

			if(v.getDomain().isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	
	
}
