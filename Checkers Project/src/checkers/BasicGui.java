package checkers;

import java.util.Iterator;
import java.util.LinkedList;

public class BasicGui {
	
	public void renderBoard(Checker[][]board) {
	
		LinkedList<String> boardDisplay = new LinkedList<String>();
		boardDisplay.add("\n         0     1     2     3     4     5     6    7    \n");
		boardDisplay.add("\n      -------------------------------------------------\n");
		for(int x = 0; x < 8; x++)
		{
			boardDisplay.add(" ");
			boardDisplay.add("  " + x + "  |  ");
			for(int y = 0; y < 8; y++)
			{
				if(board[x][y] == null)
				{
					if((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0))
					{
						boardDisplay.add("#" + "  |  ");
					}
					else if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1))
					{
						boardDisplay.add(" " + "  |  ");
					}
				}
				else if (board[x][y].color == "Red" )
				{
					if(board[x][y].isKing() == true)
					{
						boardDisplay.add("RK" + " |  ");
					}
					else
					{
						boardDisplay.add("R" + "  |  ");
					}	
				}
				else if (board[x][y].color == "Black" )
				{
					if(board[x][y].isKing() == true)
					{
						boardDisplay.add("BK" + " |  ");
					}
					else
					{
						boardDisplay.add("B" + "  |  ");
					}	
				}
				
			}
			boardDisplay.add("\n      -------------------------------------------------\n");
		}
		boardDisplay.add("\n");
		
		Iterator<String> itr = boardDisplay.iterator();
		
		while(itr.hasNext())
		{
			String output = itr.next();
			System.out.print(output);
		}
	}
}
