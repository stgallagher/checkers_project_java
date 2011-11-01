package checkers;
import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Ignore;
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
	
	@Ignore
	public void testGameOver() {
		
	}
	
	@Ignore
	public void testDisplayGameEndingMessage() {
		
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
	
	@Ignore
	public void testCreateBoard() {
		
	}
	
	@Ignore
	public void testCreateTestBoard() {
		
	}
	
	@Ignore
	public void testCreateDebugBoardAndPlay() {
		
	}
	
	@Ignore
	public void testPlaceCheckerOnBoard() {
		
	}
	
	@Ignore
	public void testPopulateCheckers() {
		
	}
	
	@Ignore
	public void testRedCheckersLeft() {
		
	}
	
	@Ignore
	public void testBlackCheckersLeft() {
		
	}
	
	@Ignore
	public void testMoveValidator() {
		
	}
	
	@Ignore
	public void testKingCheckersIfNecessary() {
		
	}
	
	@Ignore
	public void testAdjacentPositions() {
		
	}
	
	@Ignore
	public void testOpposingCheckerAdjacent() {
		
	}
	
	@Ignore
	public void testJumpLocations() {
		
	}
	
	@Ignore
	public void testJumpLocationsCoordinates() {
		
	}
	
	@Ignore
	public void testGenerateJumpLocationsCoordinatesList() {
		
	}
	
	@Ignore
	public void testJumpAvailable() {
		
	}
	
	@Ignore
	public void testJumpAvailableAndNotTaken() {
		
	}
	
	@Ignore
	public void testAttemptedJumpOfEmptySpace() {
		
	}
	@Ignore
	public void testAttemptedJumpOfOwnChecker() {
		
	}
	
	@Test
	public void testJumpingMove() {
		game.setCoordinates(2, 1, 4, 1);
		assertEquals(true, game.jumpingMove());
	}
	
	
	
	@Ignore
	public void testRemoveJumpedChecker() {
		
	}
	
	@Test
	public void testOutOfBounds() {
		assertEquals(false, game.outOfBounds(4, 4));
		assertEquals(true,  game.outOfBounds(-2, 4));
		assertEquals(true,  game.outOfBounds(2, -4));
		assertEquals(true,  game.outOfBounds(9, 4));	
	}
	
	
	@Ignore
	public void testNoCheckerAtOrigin() {
		
	}
	
	
	@Ignore
	public void testTryingToMoveOpponentsChecker() {
		
	}
	
	@Test
	public void testTryingToMoveMoreThanOneSpaceAndNotJumping() {
		game.setCoordinates(3, 3, 6, 6);
		assertEquals(true, game.tryingToMoveMoreThanOneSpaceAndNotJumping());
		game.setCoordinates(3, 3, 5, 5);
		assertEquals(false, game.tryingToMoveMoreThanOneSpaceAndNotJumping());
	}
	
	@Ignore
	public void testAttemptedNonDiagonalMove() {
		
	}
	
	
	@Ignore
	public void testAttemptedMoveToOccupiedSquare() {
		
	}
	
	@Ignore
	public void testNonKingMovingBackwards() {
		
	}
		
	@Ignore
	public void testMove() {
		
	}
	
	@Ignore
	public void testSetScanValues() {
		
	}
}
