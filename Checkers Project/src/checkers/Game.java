package checkers;

public class Game {

	int x_orig;
	int y_orig;
	int x_dest;
	int y_dest;
	String currentPlayer;
	
	// Constructor
	public Game(){
		currentPlayer = "Red";
	}
	
	// Set all coordinates individually
	public void setCoordinates(int x_origin, int y_origin, int x_destination, int y_destination) {
		x_orig = x_origin;
		y_orig = y_origin;
		x_dest = x_destination;
		y_dest = y_destination;
	}
	
	// Set all coordinates from an Array
	public void configureCoordinates(int[] coordinates) {
		x_orig = coordinates[0];
		y_orig = coordinates[1];
		x_dest = coordinates[2];
		y_dest = coordinates[3];
	}
	
	public int[] translateMoveRequestToCoordinates(String moveRequest) {
		
		String [] coordsString = moveRequest.replaceAll(" ", "").split(",");
		int [] coords = new int[coordsString.length];
		
		for(int i = 0; i < coordsString.length; i++) {
			
			coords[i] = Integer.parseInt(coordsString[i]);
		}
		return coords;
	}
	
	public void switchPlayer() {
		currentPlayer = (currentPlayer == "Red") ? "Black" : "Red";
	}
	
	public String intro() {
		return "Welcome to Checkers";
	}
	
	public String moveRequest() {
		String player = currentPlayer.toUpperCase();
		return player + " make move(x1, y1, x2, y2): ";
	}
	public boolean jumpingMove(){
		return (x_dest - x_orig) == 2;
	}
	
	public boolean outOfBounds(int x, int y) {
		return ( x < 0 || y <0) || (x > 7 || y > 7);
	}
	
	public boolean tryingToMoveMoreThanOneSpaceAndNotJumping() {
		return (Math.abs((x_dest - x_orig)) > 2);
	}
}
