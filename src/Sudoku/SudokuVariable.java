package Sudoku;
import java.util.ArrayList;

public class SudokuVariable extends Variable<Integer> {

	public static int SIZE = 9;
	
	
	public SudokuVariable(int value, ArrayList<Integer> domain) {
		super(value,domain);
		this.domain = new ArrayList<Integer>();
		if(value != 0) {
			this.domain.add(value);
		}
		else
		{
			for(int i = 0; i < SIZE; i++) {
				this.domain.add(i+1);
			}
		}
	}

}
