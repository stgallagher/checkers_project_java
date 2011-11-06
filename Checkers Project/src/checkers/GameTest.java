package checkers;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.Test;

public class GameTest extends TestCase{
	private Game game;
	
	public GameTest(String name) {
		super(name);
	}
	
	protected void setUp() {
		game = new Game();
	}
	
	@Test
	public void testConfigureCoordinates() {
		int[] coordinateArray = {3, 4, 5, 6};
		game.configureCoordinates(coordinateArray);
		assertEquals(3, game.x_orig);
		assertEquals(4, game.y_orig);
		assertEquals(5, game.x_dest);
		assertEquals(6, game.y_dest);
	}
	
	@Test
	public void testTranslateMoveRequestToCoordinates() {
		String moveRequest = "3, 4, 5, 6";
		int[] expected = {3, 4, 5, 6};
		assertArrayEquals(expected, game.translateMoveRequestToCoordinates(moveRequest));
	}
	
	@Test
	public void testGameOver() {
		assertEquals(false, game.gameOver());
		game.clearCheckers("Red");
		assertEquals(true, game.gameOver());
	}
	
	@Test
	public void testDisplayGameEndingMessage() {
		game.clearCheckers("Red");
		assertEquals("\n\nCongratulations, Black, You have won!!!", game.displayGameEndingMessage());
	}
	
	@Test
	public void testSwitchPlayer() {
		assertEquals("Red", game.currentPlayer);
		game.switchPlayer();
		assertEquals("Black", game.currentPlayer);
	}
	
	@Test
	public void testIntro() {
		assertEquals("Welcome to Checkers", game.intro());
	}
	
	@Test
	public void testMoveRequest() {
		assertEquals("RED make move(x1, y1, x2, y2): ", game.moveRequest());
	}
	
	@Test
	public void testCreateBoard() {
		assertEquals(8, game.board.length);
		assertEquals(8, game.board[3].length);
	}
	
	@Test
	public void testCreateTestBoard() {
		game.createTestBoard();
		assertEquals(0, game.redCheckersLeft());
		assertEquals(0, game.blackCheckersLeft());
	}
	
	@Test
	public void testCreateDebugBoardAndPlay() {
		
	}
	
	@Test
	public void testPlaceCheckerOnBoard() {
		Checker aChecker = new Checker(3, 3, "Red");
		game.placeCheckerOnBoard(aChecker);
		assertEquals(aChecker, game.board[3][3]);
	}
	
	
	
	@Test
	public void testPopulateCheckers() {
		assertEquals("Red", game.board[0][4].color);
		assertEquals("Red", game.board[1][3].color);
		assertEquals("Red", game.board[2][0].color);
		assertEquals("Black", game.board[5][7].color);
		assertEquals("Black", game.board[6][4].color);
		assertEquals("Black", game.board[7][3].color);
	}
	
	@Test
	public void testRedCheckersLeft() {
		assertEquals(12, game.redCheckersLeft());
	}
	
	@Test
	public void testBlackCheckersLeft() {
		assertEquals(12, game.blackCheckersLeft());
	}
	
	@Test
	public void testKingCheckersIfNecessary() {
		game.createTestBoard();
		Checker becomingKingChecker = new Checker(6, 2, "Red");
		game.placeCheckerOnBoard(becomingKingChecker);
		game.kingCheckersIfNecessary();
		assertEquals(false, becomingKingChecker.isKing());
		becomingKingChecker.x_pos = 7;
		becomingKingChecker.y_pos = 1;
		game.kingCheckersIfNecessary();
		assertEquals(true, becomingKingChecker.isKing());
	}
	
	@Test
	public void testAdjacentPositions() {
		game.createTestBoard();
		Checker referenceChecker = new Checker(4, 4, "Red");
		Checker upperLeftChecker = new Checker(5, 5, "Black");
		Checker lowerRightChecker = new Checker(3, 3, "Black");
		game.placeCheckerOnBoard(referenceChecker);
		game.placeCheckerOnBoard(upperLeftChecker);
		game.placeCheckerOnBoard(lowerRightChecker);
		game.setScanValues(4, 4);
		HashMap<String, Checker> ap = game.adjacentPositions();
		assertSame(upperLeftChecker, ap.get("upper_left"));
		assertEquals(null, ap.get("upper_right"));
		assertEquals(null, ap.get("lower_left"));
		assertSame(lowerRightChecker, ap.get("lower_right"));
	}
	
	@Test
	public void testOpposingCheckerAdjacent() {
		game.createTestBoard();
		Checker referenceChecker = new Checker(4, 4, "Red");
		Checker upperLeftChecker = new Checker(5, 5, "Black");
		Checker lowerRightChecker = new Checker(3, 3, "Black");
		game.placeCheckerOnBoard(referenceChecker);
		game.placeCheckerOnBoard(upperLeftChecker);
		game.placeCheckerOnBoard(lowerRightChecker);
		game.setScanValues(4, 4);
		HashMap<String, Boolean> ap = game.opposingCheckerAdjacent();
		assertEquals(true, (boolean)ap.get("upper_left"));
		assertEquals(false,(boolean)ap.get("upper_right"));
		assertEquals(false,(boolean)ap.get("lower_left"));
		assertEquals(true, (boolean)ap.get("lower_right"));
	}
	
	@Test
	public void testJumpLocations() {
		game.createTestBoard();
		Checker referenceChecker = new Checker(4, 4, "Red");
		Checker upperLeftChecker = new Checker(5, 5, "Black");
		Checker upperRightChecker = new Checker(5, 3, "Black");
		Checker blockingUpperRightChecker = new Checker(6, 2, "Black");
		game.placeCheckerOnBoard(referenceChecker);
		game.placeCheckerOnBoard(upperLeftChecker);
		game.placeCheckerOnBoard(upperRightChecker);
		game.placeCheckerOnBoard(blockingUpperRightChecker);
		game.setScanValues(4, 4);
		HashMap<String, Boolean> ap = game.jumpLocations();
		assertEquals(true, (boolean)ap.get("upper_left"));
		assertEquals(false, (boolean)ap.get("upper_right"));
		assertEquals(false, (boolean)ap.get("lower_left"));
		assertEquals(false, (boolean)ap.get("lower_right"));
	}
	
	@Test
	public void testJumpLocationsCoordinates() {
		game.createTestBoard();
		Checker referenceChecker = new Checker(4, 4, "Red");
		Checker upperLeftChecker = new Checker(5, 5, "Black");
		Checker upperRightChecker = new Checker(5, 3, "Black");
		game.placeCheckerOnBoard(referenceChecker);
		game.placeCheckerOnBoard(upperLeftChecker);
		game.placeCheckerOnBoard(upperRightChecker);
		game.setScanValues(4, 4);
		LinkedList<int[]> jlc = game.jumpLocationsCoordinates();
		int[] expected1 = {6, 6};
		int[] expected2 = {6, 2};
		assertArrayEquals(expected1,jlc.get(0));
		assertArrayEquals(expected2,jlc.get(1));
		assertEquals(2, jlc.size());
	}
	
	@Test
	public void testGenerateJumpLocationsCoordinatesList() {
		game.createTestBoard();
		Checker redChecker1 = new Checker(1, 1, "Red");
		Checker redChecker2 = new Checker(3, 3, "Red");
		Checker redChecker3 = new Checker(1, 7, "Red");
		Checker blackChecker1 = new Checker(4, 2, "Black");
		Checker blackChecker2 = new Checker(4, 4, "Black");
		Checker blackChecker3 = new Checker(2, 6, "Black");
		Checker blackChecker4 = new Checker(6, 6, "Black");
		game.placeCheckerOnBoard(redChecker1);
		game.placeCheckerOnBoard(redChecker2);
		game.placeCheckerOnBoard(redChecker3);
		game.placeCheckerOnBoard(blackChecker1);
		game.placeCheckerOnBoard(blackChecker2);
		game.placeCheckerOnBoard(blackChecker3);
		game.placeCheckerOnBoard(blackChecker4);
		LinkedList<LinkedList<int[]>> jlc = game.generateJumpLocationsCoordinatesList();
		int [] expected1 = {3, 5};
		int [] expected2 = {5, 5};
		int [] expected3 = {5, 1};
		boolean isExpected1There = false;
		boolean isExpected2There = false;
		boolean isExpected3There = false;
		Iterator<LinkedList<int[]>> itr = jlc.iterator();
		while(itr.hasNext())
		{
			LinkedList<int[]> next = itr.next();
			for(int i = 0; i < next.size(); i++)
			{
				int[] actual = (int[])next.get(i);
	
				if(Arrays.equals(expected1, actual))
				{
					isExpected1There = true;
				}
				if(Arrays.equals(expected2, actual))
				{
					isExpected2There = true;
				}
				if(Arrays.equals(expected3, actual))
				{
					isExpected3There = true;
				}
			}	
		}
		assertEquals(true, isExpected1There);
		assertEquals(true, isExpected2There);
		assertEquals(true, isExpected3There);
	}
	
	@Test
	public void testJumpAvailable() {
		game.createTestBoard();
		Checker redChecker = new Checker(3, 3, "Red");
		Checker blackChecker = new Checker(4, 4, "Black");
		game.placeCheckerOnBoard(redChecker);
		assertEquals(false, game.jumpAvailable());
		game.placeCheckerOnBoard(blackChecker);
		assertEquals(true, game.jumpAvailable());
	}
	
	@Test
	public void testJumpAvailableAndNotTaken() {
		game.createTestBoard();
		Checker redChecker1 = new Checker(1, 1, "Red");
		Checker redChecker2 = new Checker(1, 7, "Red");
		Checker blackChecker1 = new Checker(2, 6, "Black");
		game.placeCheckerOnBoard(redChecker1);
		game.placeCheckerOnBoard(redChecker2);
		game.placeCheckerOnBoard(blackChecker1);
		int[] coords1 = {1, 1, 2, 2};
		game.configureCoordinates(coords1);
		assertEquals(true, game.jumpAvailableAndNotTaken());
		int[] coords2 = {1, 7, 3, 5};
		game.configureCoordinates(coords2);
		assertEquals(false, game.jumpAvailableAndNotTaken());
	}
	
	@Test
	public void testAttemptedJumpOfEmptySpace() {
		int[] coords = {2, 2, 4, 4};
		game.configureCoordinates(coords);
		assertEquals("You cannot jump an empty space", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	@Test
	public void testAttemptedJumpOfOwnChecker() {
		game.createTestBoard();
		Checker jumpingChecker = new Checker(3, 3, "Red");
		Checker jumpedChecker = new Checker(4, 4, "Red");
		game.placeCheckerOnBoard(jumpingChecker);
		game.placeCheckerOnBoard(jumpedChecker);
		int[] coords = {3, 3, 5, 5};
		game.configureCoordinates(coords);
		assertEquals("You cannot jump a checker of your own color", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
		assertEquals(null, game.board[5][5]);
		assertEquals(jumpingChecker, game.board[3][3]);
		assertEquals(jumpedChecker, game.board[4][4]);
	}
	
	@Test
	public void testJumpingMove() {
		game.setCoordinates(2, 1, 4, 1);
		assertEquals(true, game.jumpingMove());
	}
	
	
	
	@Test
	public void testRemoveJumpedChecker() {
		game.createTestBoard();
		Checker jumpingChecker = new Checker(3, 3, "Red");
		Checker jumpedChecker = new Checker(4, 4, "Black");
		game.placeCheckerOnBoard(jumpingChecker);
		game.placeCheckerOnBoard(jumpedChecker);
		assertEquals(1, game.blackCheckersLeft());
		int[] coords = {3, 3, 5, 5};
		game.configureCoordinates(coords);
		assertEquals("jumping move", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
		assertEquals(jumpingChecker, game.board[5][5]);
		assertEquals(null, game.board[4][4]);
		assertEquals(0, game.blackCheckersLeft());	
	}
	
	@Test
	public void testOutOfBounds() {
		assertEquals(false, game.outOfBounds(4, 4));
		assertEquals(true,  game.outOfBounds(-2, 4));
		assertEquals(true,  game.outOfBounds(2, -4));
		assertEquals(true,  game.outOfBounds(9, 4));	
	}
	
	
	@Test
	public void testNoCheckerAtOrigin() {
		game.currentPlayer = "Red";
		int[] coords = {4, 4, 5, 5};
		game.configureCoordinates(coords);
		assertEquals("There is no checker to move in the requested location", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	
	@Test
	public void testTryingToMoveOpponentsChecker() {
		game.currentPlayer = "Red";
		int[] coords = {5, 5, 4, 4};
		game.configureCoordinates(coords);
		assertEquals("You cannot move an opponent's checker", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	@Test
	public void testTryingToMoveMoreThanOneSpaceAndNotJumping() {
		int[] coords = {2, 2, 5, 5};
		game.configureCoordinates(coords);
		assertEquals("You cannot move more than one space if not jumping", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	@Test
	public void testAttemptedNonDiagonalMove() {
		int[] coords = {2, 2, 3, 2};
		game.configureCoordinates(coords);
		assertEquals("You can only move a checker diagonally", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	
	@Test
	public void testAttemptedMoveToOccupiedSquare() {
		int[] coords = {1, 1, 2, 2};
		game.configureCoordinates(coords);
		assertEquals("You cannot move to an occupied square", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
	
	@Test
	public void testNonKingMovingBackwards() {
		game.createTestBoard();
		Checker nonKing = new Checker(4, 4, "Red");
		game.placeCheckerOnBoard(nonKing);
		int[] coords = {4, 4, 3, 3};
		game.configureCoordinates(coords);
		assertEquals("A non-king checker cannot move backwards", game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
		game.board[4][4].makeKing();
		assertEquals(null, game.moveValidator(game.x_orig, game.y_orig, game.x_dest, game.y_dest ));
	}
		
	@Test
	public void testMove() {
		Checker movingChecker = game.board[2][2];
		int[] coords = {2, 2, 3, 1}; 
		game.configureCoordinates(coords);
		game.move();
		assertEquals(movingChecker, game.board[3][1]);
		assertEquals(null, game.board[2][2]);
	}
	
	@Test
	public void testSetScanValues() {
		int x = 5;
		int y = 4;
		game.setScanValues(x, y);
		assertEquals(5, game.x_scan);
		assertEquals(4, game.y_scan);
	}
}
