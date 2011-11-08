package checkers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {

	int x_orig;
	int y_orig;
	int x_dest;
	int y_dest;
	int x_scan;
	int y_scan;
	
	String currentPlayer;
	
	Checker[][] board = new Checker[8][8];
	ArrayList<Checker> redCheckers = new ArrayList<Checker>();
	ArrayList<Checker> blackCheckers = new ArrayList<Checker>();
	BasicGui gui = new BasicGui();
	
	boolean consecutiveJumps = false;
	Checker consecutiveJumper = null;
	
	// Constructor
	public Game(){
		currentPlayer = "Red";
		populateCheckers();
	}
	
	
	public void playGame() {
		System.out.println(intro());
		
		while(gameOver() == false)
		{
			String message = null;
			gui.renderBoard(board);
			System.out.print(moveRequest());
			
			Scanner scanner = new Scanner (System.in);
			String playerInput = scanner.nextLine();
			int[] coordinates = new int[4]; 
			coordinates = this.translateMoveRequestToCoordinates(playerInput);
			this.configureCoordinates(coordinates);
			
			message = this.moveValidator(x_orig, y_orig, x_dest, y_dest);

			System.out.println(message);
		}
		gui.renderBoard(board);
		System.out.println(this.displayGameEndingMessage());
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
	
	public boolean gameOver() {
		return ((redCheckersLeft() == 0) || (blackCheckersLeft() == 0));
	}
	
	public String displayGameEndingMessage() {
		String winner = (redCheckersLeft() == 0) ? "Black" : "Red";
		return "\n\nCongratulations, " + winner + ", You have won!!!";
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
	
	public void createTestBoard() {
		this.clearCheckers();
	}
	
	public void createDebugBoardAndPlay() {
		this.createTestBoard();
		Checker redChecker1 = new Checker(2, 4, "Red");
		Checker redChecker2 = new Checker(3, 3, "Red");
		Checker redChecker3 = new Checker(1, 1, "Red");
		Checker blackChecker1 = new Checker(5, 3, "Black");
		this.placeCheckerOnBoard(redChecker1);
		this.placeCheckerOnBoard(redChecker2);
		this.placeCheckerOnBoard(redChecker3);
		this.placeCheckerOnBoard(blackChecker1);
	    this.playGame();
	}
	
	public void placeCheckerOnBoard(Checker aChecker) {
		if(aChecker.color == "Red")
		{
			redCheckers.add(aChecker);
		}
		else
		{
			blackCheckers.add(aChecker);
		}
		board[aChecker.x_pos][aChecker.y_pos] = aChecker;
	}
	
	
	public void populateCheckers() {
		
		int[] evens = {0, 2, 4, 6};
		int[] odds  = {1, 3, 5, 7};
		
		for(int i = 0; i < 3; i++)
		{
			if((i % 2) == 0)
			{
				for(int j = 0; j < evens.length; j++)
				{
					Checker redChecker = new Checker(i, evens[j], "Red");
					board[i][evens[j]] = redChecker;
					redCheckers.add(redChecker);
				}
			}
			if((i % 2) == 1)
			{	
				for(int k = 0; k < odds.length; k++)
				{
					Checker redChecker = new Checker(i, odds[k], "Red");
					board[i][odds[k]] = redChecker;
					redCheckers.add(redChecker);
				}
			}
		}
		
		for(int i = 5; i < 8; i++)
		{
			if((i % 2) == 0)
			{
				for(int j = 0; j < evens.length; j++)
				{
					Checker blackChecker = new Checker(i, evens[j], "Black");
					board[i][evens[j]] = blackChecker;
					blackCheckers.add(blackChecker);
				}
			}
			if((i % 2) == 1)
			{
				for(int k = 0; k < odds.length; k++)
				{
					Checker blackChecker = new Checker(i, odds[k], "Black");
					board[i][odds[k]] = blackChecker;
					blackCheckers.add(blackChecker);
				}
			}
		}
	}
	
	public void clearCheckers() {
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				board[i][j] = null;
			}
		}
	}
	
	public void clearCheckers(String checkerColor) {
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if((board[i][j] != null) && (board[i][j].color == checkerColor))
				{
					board[i][j] = null;
				}
			}
		}
	}
	
	public int redCheckersLeft() {
		int redCount = 0;
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j <board.length; j++)
			{
				if((board[i][j] != null) && (board[i][j].color == "Red"))
				{
					redCount += 1;
				}					
			}
		}
		return redCount;
	}
	
	public int blackCheckersLeft() {
		int blackCount = 0;
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j <board.length; j++)
			{
				if((board[i][j] != null) && (board[i][j].color == "Black"))
				{
					blackCount += 1;
				}					
			}
		}
		return blackCount;
	}
	
	public String moveValidator(int x_origin, int y_origin, int x_destination, int y_destination) {
		x_orig = x_origin;
		y_orig = y_origin;
		x_dest = x_destination;
		y_dest = y_destination;
		
		String message = null;
		
		if(consecutiveJumps == true && board[x_orig][y_orig] != consecutiveJumper)
		{
			return message = "When consecutive jumping, you must jump with the same checker";
		}
		if(outOfBounds(x_dest, x_orig) == true)
		{
			message = "You cannot move off the board";
		}
		else if(noCheckerAtOrigin() == true)
		{
			message = "There is no checker to move in the requested location";
		}
		else if(tryingToMoveOpponentsChecker() == true)
		{
			message = "You cannot move an opponent's checker";
		}
		else if(tryingToMoveMoreThanOneSpaceAndNotJumping() == true)
		{
			message = "You cannot move more than one space if not jumping";
		}
		else if(attemptedNonDiagonalMove() == true)
		{
			message = "You can only move a checker diagonally";
		}
		else if(attemptedMoveToOccupiedSquare() == true)
		{
			message = "You cannot move to an occupied square";
		}
		else if(nonKingMovingBackwards() == true)
		{
			message = "A non-king checker cannot move backwards";
		}
		else if(attemptedJumpOfEmptySpace() == true)
		{
			message = "You cannot jump an empty space";
		}
		else if(attemptedJumpOfOwnChecker() == true)
		{
			message = "You cannot jump a checker of your own color";
		}
		else if(jumpAvailableAndNotTaken() == true)
		{
			message = "You must jump if a jump is available";
		}
		else
		{
			move();
			
			if(jumpingMove() == true)
			{
				message = "jumping move";
				removeJumpedChecker();
				consecutiveJumps = false;
				
				if(anyMoreJumpsForThisChecker() == true)
				{
					consecutiveJumps = true;
					consecutiveJumper = board[x_dest][y_dest];
				}
			}
			kingCheckersIfNecessary();
		}
		
		if((consecutiveJumps == false) && (message == null || message == "jumping move"))
		{
			this.switchPlayer();
		}
		return message;
	}
	
	public boolean anyMoreJumpsForThisChecker() {
		
		this.setScanValues(x_dest, y_dest);
		HashMap<String, Boolean> jlc = this.jumpLocations();
		return (jlc.containsValue(true)) ? true : false;
	}
	
	public void kingCheckersIfNecessary() {
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if((board[i][j] != null) && (board[i][j].color == "Red") && (board[i][j].x_pos == 7))
				{
					board[i][j].makeKing();
				}
				else if((board[i][j] != null) && (board[i][j].color == "Black") && (board[i][j].x_pos == 0))
				{
					board[i][j].makeKing();
				}
			}
		}
	}
	
	public HashMap<String, Checker> adjacentPositions() {
		
		HashMap<String, Checker> jumpPositions = new HashMap<String, Checker>();
		
		if(currentPlayer == "Red")
		{
			if((x_scan  > 0 && x_scan < 7) && (y_scan  > 0 && y_scan < 7))
			{	 
				jumpPositions.put("upper_left",  board[x_scan + 1][y_scan + 1]);
				jumpPositions.put("upper_right", board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_left",  board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_right", board[x_scan - 1][y_scan - 1]);
			}
			else if((x_scan  == 0) && (y_scan  > 0 && y_scan < 7))
			{
				jumpPositions.put("upper_left",  board[x_scan + 1][y_scan + 1]);
				jumpPositions.put("upper_right", board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  == 0) && (y_scan == 7))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  > 0 && x_scan < 7) && (y_scan == 7))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", board[x_scan - 1][y_scan - 1]);
			}
			else if((x_scan == 7) && (y_scan == 7))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", board[x_scan - 1][y_scan - 1]);
			}
			else if((x_scan == 7) && (y_scan  > 0 && y_scan < 7))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_right", board[x_scan - 1][y_scan - 1]);
			}
			else if((x_scan == 7) && (y_scan == 0))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  > 0 && x_scan < 7) && (y_scan == 0))
			{	 
				jumpPositions.put("upper_left",  board[x_scan + 1][y_scan + 1]);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  == 0) && (y_scan == 0))
			{	 
				jumpPositions.put("upper_left",  board[x_scan + 1][y_scan + 1]);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}	
		}
		
		if(currentPlayer == "Black")
		{
			if((x_scan  > 0 && x_scan < 7) && (y_scan  > 0 && y_scan < 7))
			{	 
				jumpPositions.put("upper_left",  board[x_scan - 1][y_scan - 1]);
				jumpPositions.put("upper_right", board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_left",  board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_right", board[x_scan + 1][y_scan + 1]);
			}
			else if((x_scan  == 7) && (y_scan  > 0 && y_scan < 7))
			{
				jumpPositions.put("upper_left",  board[x_scan - 1][y_scan - 1]);
				jumpPositions.put("upper_right", board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  == 7) && (y_scan == 0))
			{	
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  > 0 && x_scan < 7) && (y_scan == 0))
			{
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", board[x_scan - 1][y_scan + 1]);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", board[x_scan + 1][y_scan + 1]);
			}
			else if((x_scan == 0) && (y_scan == 0))
			{	 
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", board[x_scan + 1][y_scan + 1]);
			}
			else if((x_scan == 0) && (y_scan  > 0 && y_scan < 7))
			{
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_right", board[x_scan + 1][y_scan + 1]);
			}
			else if((x_scan == 0) && (y_scan == 7))
			{
				jumpPositions.put("upper_left",  null);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan  > 0 && x_scan < 7) && (y_scan == 7))
			{
				jumpPositions.put("upper_left",  board[x_scan - 1][y_scan - 1]);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  board[x_scan + 1][y_scan - 1]);
				jumpPositions.put("lower_right", null);
			}
			else if((x_scan == 7) && (y_scan == 7))
			{
				jumpPositions.put("upper_left",  board[x_scan - 1][y_scan - 1]);
				jumpPositions.put("upper_right", null);
				jumpPositions.put("lower_left",  null);
				jumpPositions.put("lower_right", null);
			}
		}
		return jumpPositions;
	}
	
	public HashMap<String, Boolean> opposingCheckerAdjacent() {
		
		HashMap<String, Checker> adjPos = adjacentPositions();
		
		HashMap<String, Boolean> opposingCheckers = new HashMap<String, Boolean>();
		
		opposingCheckers.put("upper_left", new Boolean(false));
		opposingCheckers.put("upper_right", new Boolean(false));
		opposingCheckers.put("lower_left", new Boolean(false));
		opposingCheckers.put("lower_right", new Boolean(false));
		
		if((adjPos.get("upper_left") != null) && (adjPos.get("upper_left").color != currentPlayer))
		{
			opposingCheckers.put("upper_left", true);
		}
		if((adjPos.get("upper_right") != null) && (adjPos.get("upper_right").color != currentPlayer))
		{
			opposingCheckers.put("upper_right", true);
		}
		if((adjPos.get("lower_left") != null) && (adjPos.get("lower_left").color != currentPlayer))
		{
			opposingCheckers.put("lower_left", true);
		}
		if((adjPos.get("lower_right") != null) && (adjPos.get("lower_right").color != currentPlayer))
		{
			opposingCheckers.put("lower_right", true);
		}
		return opposingCheckers;
	}
	
	public HashMap<String, Boolean> jumpLocations() {
		
		boolean outsideBounds;
		
		HashMap<String, Boolean> oppCheck = opposingCheckerAdjacent();
		
		HashMap<String, Boolean> jumpLocations = new HashMap<String, Boolean>();
		
		jumpLocations.put("upper_left", new Boolean(false));
		jumpLocations.put("upper_right", new Boolean(false));
		jumpLocations.put("lower_left", new Boolean(false));
		jumpLocations.put("lower_right", new Boolean(false));
		
		Checker checker = board[x_scan][y_scan];
		
		if(currentPlayer == "Red")
		{
			outsideBounds = outOfBounds(x_scan + 2, y_scan + 2);
			
			if((oppCheck.get("upper_left").equals(true)) && (outsideBounds == false) && (board[x_scan + 2][y_scan + 2] == null))
			{
				jumpLocations.put("upper_left", true);
			}
		
			outsideBounds = outOfBounds(x_scan + 2, y_scan - 2);
		
			if((oppCheck.get("upper_right").equals(true)) && (outsideBounds == false) && (board[x_scan + 2][y_scan - 2] == null))
			{
				jumpLocations.put("upper_right", true);
			}
			
			if(checker != null && checker.isKing())
			{
				outsideBounds = outOfBounds(x_scan - 2, y_scan + 2);
			
				if((oppCheck.get("lower_left").equals(true)) && (outsideBounds == false) && (board[x_scan - 2][y_scan + 2] == null))
				{
					jumpLocations.put("lower_left", true);
				}
			
				outsideBounds = outOfBounds(x_scan - 2, y_scan - 2);
			
				if((oppCheck.get("lower_right").equals(true)) && (outsideBounds == false) && (board[x_scan - 2][y_scan - 2] == null))
				{
					jumpLocations.put("lower_right", true);
				}
			}
		}
		
		if(currentPlayer == "Black")
		{
			
			outsideBounds = outOfBounds(x_scan - 2, y_scan - 2);
		
			if((oppCheck.get("upper_left").equals(true)) && (outsideBounds == false) && (board[x_scan - 2][y_scan - 2] == null))
			{
				jumpLocations.put("upper_left", true);
			}
		
			outsideBounds = outOfBounds(x_scan - 2, y_scan + 2);
		
			if((oppCheck.get("upper_right").equals(true)) && (outsideBounds == false) && (board[x_scan - 2][y_scan + 2] == null))
			{
				jumpLocations.put("upper_right", true);
			}
			
			if(checker != null && checker.isKing())
			{
				outsideBounds = outOfBounds(x_scan + 2, y_scan - 2);
			
				if((oppCheck.get("lower_left").equals(true)) && (outsideBounds == false) && (board[x_scan + 2][y_scan - 2] == null))
				{
					jumpLocations.put("lower_left", true);
				}
			
				outsideBounds = outOfBounds(x_scan + 2, y_scan + 2);
			
				if((oppCheck.get("lower_right").equals(true)) && (outsideBounds == false) && (board[x_scan + 2][y_scan + 2] == null))
				{
					jumpLocations.put("lower_right", true);
				}
			}
		}
		return jumpLocations;
	}
	
	public LinkedList<int[]> jumpLocationsCoordinates() {
		
		HashMap<String, Boolean> jumpLoc = jumpLocations();
		
		LinkedList<int[]> jumpingLocations = new LinkedList<int[]>();
		
		if (currentPlayer == "Red")
		{
			if( jumpLoc.get("upper_left").equals(true))
			{  
				int[] locationCoords = {x_scan + 2, y_scan + 2};
				jumpingLocations.add(locationCoords);
			}
	  
			if( jumpLoc.get("upper_right").equals(true))
			{
				int[] locationCoords = {x_scan + 2, y_scan - 2};
				jumpingLocations.add(locationCoords); 
			}
			if( jumpLoc.get("lower_left").equals(true))
			{
				int[] locationCoords = {x_scan - 2, y_scan + 2};
				jumpingLocations.add(locationCoords);
			}
			if( jumpLoc.get("lower_right").equals(true))
			{
				int[] locationCoords = {x_scan - 2, y_scan - 2};
				jumpingLocations.add(locationCoords);
			}
		}

		if (currentPlayer == "Black")
		{
			if( jumpLoc.get("upper_left").equals(true))
			{  
				int[] locationCoords = {x_scan - 2, y_scan - 2};
				jumpingLocations.add(locationCoords);
			}
	  
			if( jumpLoc.get("upper_right").equals(true))
			{
				int[] locationCoords = {x_scan - 2, y_scan + 2};
				jumpingLocations.add(locationCoords); 
			}
			if( jumpLoc.get("lower_left").equals(true))
			{
				int[] locationCoords = {x_scan + 2, y_scan - 2};
				jumpingLocations.add(locationCoords);
			}
			if( jumpLoc.get("lower_right").equals(true))
			{
				int[] locationCoords = {x_scan + 2, y_scan + 2};
				jumpingLocations.add(locationCoords);
			}
		}
		return jumpingLocations;
	}
	
	public LinkedList<LinkedList<int[]>> generateJumpLocationsCoordinatesList() {
		
		LinkedList<LinkedList<int[]>> coordinatesList = new LinkedList<LinkedList<int[]>>();
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				if((board[i][j] != null) && (board[i][j].color == currentPlayer))
				{					
					x_scan = board[i][j].x_pos;
					y_scan = board[i][j].y_pos;
					coordinatesList.add(this.jumpLocationsCoordinates());
				}
			}
		}
		
		return coordinatesList;
	}
	
	public boolean jumpAvailable() {
		
		boolean available = false;
		
		LinkedList<LinkedList<int[]>> jumpList = generateJumpLocationsCoordinatesList();
		
		Iterator<LinkedList<int[]>> itr = jumpList.iterator();
		
		while(itr.hasNext())
		{
			LinkedList<int[]> next = itr.next();
			for(int i = 0; i < next.size(); i++)
			{
				int[] actual = (int[])next.get(i);
				if(actual.length > 0)
				{
					available = true;
				}
			}	
		}
		return available;
	}
	
	public boolean jumpAvailableAndNotTaken() {
		
		boolean notTakenJump = true;
		
		LinkedList<LinkedList<int[]>> jumpPossible = generateJumpLocationsCoordinatesList();
		
		Iterator<LinkedList<int[]>> itr = jumpPossible.iterator();
		
		while(itr.hasNext())
		{
			LinkedList<int[]> next = itr.next();
			for(int i = 0; i < next.size(); i++)
			{
				int[] actual = (int[])next.get(i);
				
				for(int j = 0; j < actual.length; j += 2)
				{
					if((x_dest == actual[j]) && (y_dest == actual[j + 1]))
					{
						notTakenJump = false;
					}
				}
			}	
		}
		//System.out.println("IN JUMP AVAIL: jumplist size = " + jumpPossible.size());
		
		return ((jumpAvailable() == true) && (notTakenJump));
	}
	
	public boolean attemptedJumpOfEmptySpace() {
		if(jumpingMove())
		{
			int x_delta = (x_dest > x_orig) ? 1 : -1;
			int y_delta = (y_dest > y_orig) ? 1 : -1;
			
			if(currentPlayer == "Black")
			{
				x_delta = (x_dest < x_orig) ? -1 : 1;
				y_delta = (y_dest < y_orig) ? -1 : 1;
			}
			
			int jumpedCheckerXvalue = x_orig + x_delta;
			int jumpedCheckerYvalue = y_orig + y_delta;
			
			Checker jumpedChecker = board[jumpedCheckerXvalue][jumpedCheckerYvalue];
			return (jumpedChecker == null);
		}
		return false;
	}
	
	public boolean attemptedJumpOfOwnChecker() {
		if(jumpingMove())
		{
			int x_delta = (x_dest > x_orig) ? 1 : -1;
			int y_delta = (y_dest > y_orig) ? 1 : -1;
			
			if(currentPlayer == "Black")
			{
				x_delta = (x_dest < x_orig) ? -1 : 1;
				y_delta = (y_dest < y_orig) ? -1 : 1;
			}
			
			int jumpedCheckerXvalue = x_orig + x_delta;
			int jumpedCheckerYvalue = y_orig + y_delta;
			
			Checker jumpedChecker = board[jumpedCheckerXvalue][jumpedCheckerYvalue];
			
			Checker jumpingChecker = board[x_orig][y_orig];
			return (jumpedChecker.color == jumpingChecker.color);
		}
		return false;
	}
	
	public boolean jumpingMove(){
		return Math.abs(x_dest - x_orig) == 2;
	}
	
	public String removeJumpedChecker() {
		int x_delta = (x_dest > x_orig) ? 1 : -1;
		int y_delta = (y_dest > y_orig) ? 1 : -1;
		
		int removeCheckerXvalue = x_orig + x_delta;
		int removeCheckerYvalue = y_orig + y_delta;
		
		String removedCheckerType = (board[removeCheckerXvalue][removeCheckerYvalue].isKing()) ? "King" : "Standard";
		board[removeCheckerXvalue][removeCheckerYvalue] = null;
		
		return removedCheckerType;
	}
	
	public boolean outOfBounds(int x, int y) {
		return ( x < 0 || y <0) || (x > 7 || y > 7);
	}
	
	public boolean noCheckerAtOrigin() {
		return board[x_orig][y_orig] == null;
	}
	
	public boolean tryingToMoveOpponentsChecker() {
		return currentPlayer != board[x_orig][y_orig].color;
	}
	
	
	public boolean tryingToMoveMoreThanOneSpaceAndNotJumping() {
		return (Math.abs((x_dest - x_orig)) > 2);
	}
	
	public boolean attemptedNonDiagonalMove() {
		return (x_orig == x_dest) || (y_orig == y_dest);
	}
	
	public boolean attemptedMoveToOccupiedSquare() {
		return board[x_dest][y_dest] != null;
	}
	
	public boolean nonKingMovingBackwards() {
		if(currentPlayer == "Red")
		{
	      return ((x_dest < x_orig) && (board[x_orig][y_orig].isKing() == false));
		}
	    else
	    {
	      return ((x_dest > x_orig) && (board[x_orig][y_orig].isKing() == false));
	    }
	}
	
	public void move() {
		Checker movingChecker = board[x_orig][y_orig];
		
		movingChecker.x_pos = x_dest;
		movingChecker.y_pos = y_dest;
		
		board[x_orig][y_orig] = null;
		board[x_dest][y_dest] = movingChecker;
	}
	
	public void setScanValues(int x, int y) {
		x_scan = x;
		y_scan = y;
	}
}
