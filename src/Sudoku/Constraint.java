package Sudoku;
import java.util.ArrayList;

public abstract class Constraint<T> {
	
	public abstract boolean check();
	
	public abstract boolean filterDomain(Variable<T> variable);
	
	
}
