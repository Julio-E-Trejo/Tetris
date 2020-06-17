package Tetris;

import java.awt.Color;
import java.awt.Graphics;

public class TetrisMethods {
	
	private Color[][] colors;
	
	private int pm;
	
	/**Clears a row and shifts all rows down by one.
	 * @param board the 2-D array of integers that acts as the game board
	 * @return the modified game board array*/
	public int[][] clearRow(int[][] board, Color[][] cBoard)
	{
		pm = 0;
		for(int row = board.length - 1; row > 0; row --)
		{
			if(checkRow(board, row))
			{
				if(row - 1 >= 0)
					for(int r = row; r - 1 > 0; r --)
						for(int index = 0; index < board[row].length; index ++)
						{
							if(board[r][index] > 1 || board[r - 1][index] > 1)
								continue;
							else
							{
								board[r][index] = board[r - 1][index];
								cBoard[r][index] = cBoard[r - 1][index];
							}
						}
				else
					for(int index = 0; index < board[row].length; index ++)
					{
						board[row][index] = 0; cBoard[row][index] = null;
					}
				pm ++;
			}
			else
				continue;
		}
		colors = cBoard;
		return board;
	}
	
	/**Increases the points for Tetris according to how many
	 * lines are cleared.
	 * @return the modified points*/
	public int upPoints(int points)
	{
		return points + pm * 1000 + 1;
	}
	
	/**@return the colors for the board*/
	public Color[][] getColors()
	{
		return colors;
	}
	
	/**Generates a random color with the use of RGB.
	 * @return a color created from a mix of red, green, and blue*/
	public Color colorGen()
	{
		int x = (int)(Math.random() * 256);
		int y = (int)(Math.random() * 256);
		int z = (int)(Math.random() * 256);
		return new Color(x, y, z);
	}
	
	/**Creates the next block in the top right corner of the screen.
	 * @param g the graphics component
	 * @param block the number assigned to the next designated block
	 * @param x the x-coordinate of the block
	 * @param y the y-coordinate of the block
	 * @param space the size of each segment of the block*/
	public void showNextBlock(Graphics g, int block, int x, int y, int space)
	{
		g.setColor(colorGen());
		g.drawRect(x - space * 3, y - space * 4, space * 5, space * 5);
		switch(block) {
			case 2:
				drawZ(g, x, y, space);
				break;
			case 3:
				drawT(g, x, y, space);
				break;
			case 4:
				drawL(g, x, y, space);
				break;
			case 5:
				drawRL(g, x, y, space);
				break;
			case 6:
				drawLine(g, x, y, space);
				break;
			case 7:
				drawBox(g, x, y, space);
				break;
			default:
				drawS(g, x, y, space);
				break;
		}
	}
	
	private void drawS(Graphics g, int x, int y,  int space) {
		g.setColor(Color.YELLOW);
		g.fillRect(x - space, y - space, space * 2, space);
		g.fillRect(x - space * 2, y, space * 2, space);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space, space * 2, space);
		g.drawRect(x - space * 2, y + space, space * 2, space);
		g.drawRect(x - space, y - space, space, space * 2);
	}

	private void drawBox(Graphics g, int x, int y, int space) {
		g.setColor(Color.BLUE);
		g.fillRect(x - space, y - space, space * 2, space * 2);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space, space * 2, space);
		g.drawRect(x - space, y, space * 2, space);
		g.drawRect(x - space, y - space, space, space * 2);
	}

	private void drawLine(Graphics g, int x, int y, int space) {
		g.setColor(Color.ORANGE);
		g.fillRect(x - space, y - space * 3, space, space * 4);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space * 3, space, space * 2);
		g.drawRect(x - space, y- space * 2, space, space * 2);
		g.drawRect(x - space, y - space, space, space * 2);
	}

	private void drawRL(Graphics g, int x, int y, int space) {
		g.setColor(Color.GREEN);
		g.fillRect(x - space, y - space * 2, space, space * 2);
		g.fillRect(x - space * 2, y, space * 2, space);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space * 2, space, space * 2);
		g.drawRect(x - space * 2, y, space * 2, space);
	}

	private void drawL(Graphics g, int x, int y, int space) {
		g.setColor(Color.RED);
		g.fillRect(x - space, y - space * 2, space, space * 2);
		g.fillRect(x - space, y, space * 2, space);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space * 2, space, space * 2);
		g.drawRect(x - space, y, space * 2, space);
	}

	private void drawT(Graphics g, int x, int y, int space) {
		g.setColor(Color.MAGENTA);
		g.fillRect(x - space, y - space, space, space);
		g.fillRect(x - space * 2, y, space * 3, space);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y - space, space, space * 2);
		g.drawRect(x - space * 2, y, space * 3, space);
	}

	private void drawZ(Graphics g, int x, int y, int space) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x - space, y, space * 2, space);
		g.fillRect(x - space * 2, y - space, space * 2, space);
		g.setColor(Color.BLACK);
		g.drawRect(x - space, y, space * 2, space);
		g.drawRect(x - space * 2, y - space, space * 2, space);
		g.drawRect(x - space, y - space, space, space * 2);
	}

	/**Checks to see if the current row is all empty.
	 * @param board the Tetris board as a 2-D integer array
	 * @param row the row being checked
	 * @return true if there is a non-moving block (value of 1);
	 * false otherwise*/
	private boolean checkRow(int[][] board, int row)
	{
		for(int index : board[row])
			if(index == 0 || index > 1)
				return false;
		return true;
	}
	
}
