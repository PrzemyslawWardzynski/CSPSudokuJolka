package Jolka;

import java.util.ArrayList;

public class JVariable extends Variable<String>{
	
	private int startX;
	private int startY;
	private int length;
	private Direction direction;
	public JVariable(String value, ArrayList<String> domain, int startX, int startY, int length, Direction direction) {
		super(value,domain);
		this.startX = startX;
		this.startY = startY;
		this.length = length;
		this.direction = direction;
	}

	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
		
	public int getLength() {
		return length;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public boolean inRange(int i, int j) {
		if(direction == Direction.ACROSS) {
			if(i == startX && j >= startY && j <= startY+length-1) return true;
		}
		else {
			if(j == startY && i >= startX && i <= startX+length-1) return true;
		}
		return false;
	}
	
}
