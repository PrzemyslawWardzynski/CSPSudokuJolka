package Jolka;

import java.util.ArrayList;

public class EqualConstraint extends Constraint<String> {

	protected ArrayList<JVariable> variables;
	
	public ArrayList<JVariable> getVariables() {
		return variables;
	}
	
	
	
	private int firstWordPosition;
	private int secondWordPosition;
	
	public EqualConstraint(ArrayList<JVariable> variables, int firstWordPosition, int secondWordPosition) {
		this.variables = variables;
		this.setFirstWordPosition(firstWordPosition);
		this.setSecondWordPosition(secondWordPosition);
	}
	
	
	
	
	

	@Override
	public boolean check() {
		
		if(variables.get(0).getValue() == null || variables.get(1).getValue() == null) return true;
		
		if(variables.get(0).getValue().charAt(getFirstWordPosition()) == 
		   variables.get(1).getValue().charAt(getSecondWordPosition()))
			return true;
			
		
		return false;
	}

	@Override
	public boolean filterDomain(Variable<String> var) {
		var = (JVariable)var;
		for(JVariable v : getVariables()) {
			if(!v.equals(var)) {
				ArrayList<String> domain = v.getDomain();

				for(int k = 0; k < domain.size(); k++) {
					
					String value = domain.get(k);
					
					if(((JVariable)var).getDirection() == Direction.ACROSS) {
						char firstWord = var.getValue().charAt(getFirstWordPosition());
						int secondWordIndex = getSecondWordPosition();

						if(value.charAt(secondWordIndex) != firstWord) {
							domain.remove(value);
							k--;
						}
					}
					else {
						char secondWord = var.getValue().charAt(getSecondWordPosition());
						int firstWordIndex = getFirstWordPosition();

						if(value.charAt(firstWordIndex) != secondWord) {
							domain.remove(value);
							k--;
						}
					}
					
					
					
				}

				if(v.getDomain().isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}




	public int getFirstWordPosition() {
		return firstWordPosition;
	}




	public void setFirstWordPosition(int firstWordPosition) {
		this.firstWordPosition = firstWordPosition;
	}




	public int getSecondWordPosition() {
		return secondWordPosition;
	}




	public void setSecondWordPosition(int secondWordPosition) {
		this.secondWordPosition = secondWordPosition;
	}

}
