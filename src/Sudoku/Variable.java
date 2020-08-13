package Sudoku;
import java.util.ArrayList;

public abstract class Variable<T> {

	protected T value;
	protected ArrayList<T> domain;
	
	public Variable(T value, ArrayList<T> domain){
		this.value = value;
		this.domain = domain;
	}
	
	
	public T getValue() {
		return value;
	}

	public ArrayList<T> getDomain() {
		return domain;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setDomain(ArrayList<T> domain) {
		this.domain = domain;
	}
	
}
