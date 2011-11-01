package checkers;

public class Checker {
	
	int x_pos;
	int y_pos;
	String color;
	boolean kingStatus;
	
	public Checker(int x_Location, int y_Location, String theColor) {
		x_pos = x_Location;
		y_pos = y_Location;
		color = theColor;
		kingStatus = false;
	}
	
	public boolean isKing() {
		return kingStatus;
	}
	
	public void makeKing() {
		this.kingStatus = true;
	}
}
