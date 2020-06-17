package Tetris;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TetrisControls extends JPanel implements KeyListener, ActionListener{
	private int velX, LB, RB, UB, BB, mid = ((LB + RB) / 2) ;
	
	public int points, greatestPoints, nextBlock;
	
	public final int space;
	
	public int minX = 20, minY = 20, maxX, maxY;
	
	private int time, totalTime;
	
	private int[][] board;
	
	private Color[][] blockColor;
	
	private static int blockPos = 0;
	
	private Timer watch;
	
	private TetrisBlocks blocks;
	
	private TetrisMethods clear;
	
	private boolean skip;
	
	private double speed;
	
	public TetrisControls(int prefD, int rows, int cols)
	{
		skip = false;
		if(prefD >= 20)
		{
			watch = new Timer(prefD, this);
			speed = prefD;
		}
		else
		{
			watch = new Timer(20, this);
			speed = 50;
		}
		watch.start();
		if(rows < 20)
			rows = 20;
		if(cols < 6)
			cols = 6;
		board = board(rows, cols);
		space = 1000 / rows;
		maxX = board[0].length * space + minX;
		maxY = board.length * space + minX;
		velX = 0;
		LB = cols / 2 - 2;
		RB = cols / 2 + 1;
		UB = 0;
		BB = 4;
		points = 0; greatestPoints = 0;
		blocks = new TetrisBlocks();
		board = blocks.blockGenerator(board, (int)(Math.random() * 7 + 2));
		blockColor = new Color[board.length][board[0].length];
		assignColor(board[2][board[0].length / 2]);
		clear = new TetrisMethods();
		mid = ((LB + RB) / 2);
		nextBlock = (int)(Math.random() * 7 + 2);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if(checkFill())
			newGame();
		if(time <= totalTime && !skip)
		{
			moveDown();
			time = totalTime + watch.getDelay() * 10;
			board = clear.clearRow(board, blockColor);
			points = clear.upPoints(points);
			if(speed > 30)
			{
				speed -= 0.01;
			}
		}
		else if(skip)
			moveDown();
		totalTime += watch.getDelay();
		blockColor = clear.getColors();
		mid = ((LB + RB) / 2);
	}
	
	/**Assigns the appropriate color to the correct spots for the generated block.
	 * @param blockNum the distinguishing value of each block*/
	private void assignColor(int blockNum)
	{
		switch(blockNum)
		{
			case 3:
				blockColor[1][board[0].length / 2] = Color.GREEN;
				blockColor[2][board[0].length / 2] = Color.GREEN;
				blockColor[3][board[0].length / 2] = Color.GREEN;
				blockColor[3][board[0].length / 2 - 1] = Color.GREEN;
				break;
			case 4:
				blockColor[1][board[0].length / 2] = Color.RED;
				blockColor[2][board[0].length / 2] = Color.RED;
				blockColor[3][board[0].length / 2] = Color.RED;
				blockColor[3][board[0].length / 2 + 1] = Color.RED;
				break;
			case 5:
				blockColor[2][board[0].length / 2 + 1] = Color.MAGENTA;
				blockColor[2][board[0].length / 2 - 1] = Color.MAGENTA;
				blockColor[2][board[0].length / 2] = Color.MAGENTA;
				blockColor[3][board[0].length / 2] = Color.MAGENTA;
				break;
			case 6:
				blockColor[2][board[0].length / 2 - 1] = Color.LIGHT_GRAY;
				blockColor[2][board[0].length / 2] = Color.LIGHT_GRAY;
				blockColor[3][board[0].length / 2] = Color.LIGHT_GRAY;
				blockColor[3][board[0].length / 2 + 1] = Color.LIGHT_GRAY;
				break;
			case 7:
				blockColor[2][board[0].length / 2 + 1] = Color.YELLOW;
				blockColor[2][board[0].length / 2] = Color.YELLOW;
				blockColor[3][board[0].length / 2] = Color.YELLOW;
				blockColor[3][board[0].length / 2 - 1] = Color.YELLOW;
				break;
			case 8:
				blockColor[2][board[0].length / 2] = Color.BLUE;
				blockColor[2][board[0].length / 2 + 1] = Color.BLUE;
				blockColor[3][board[0].length / 2] = Color.BLUE;
				blockColor[3][board[0].length / 2 + 1] = Color.BLUE;
				break;
			default:
				blockColor[0][board[0].length / 2] = Color.ORANGE;
				blockColor[1][board[0].length / 2] = Color.ORANGE;
				blockColor[2][board[0].length / 2] = Color.ORANGE;
				blockColor[3][board[0].length / 2] = Color.ORANGE;
				break;
		}
	}
	
	/**Assigns the appropriate color to the current block on the board.*/
	private void coloring()
	{
		Color c;
		switch(board[BB - 2][mid])
		{
			case 3:
				c = Color.GREEN;
				break;
			case 4:
				c = Color.RED;
				break;
			case 5:
				c = Color.MAGENTA;
				break;
			case 6:
				c = Color.LIGHT_GRAY;
				break;
			case 7:
				c = Color.YELLOW;
				break;
			case 8:
				c = Color.BLUE;
				break;
			default:
				c = Color.ORANGE;
				break;
		}
		for(int row = UB; row <= BB && row < board.length; row ++)
			for(int col = LB; col <= RB; col ++)
			{
				if(board[row][col] == 0)
					blockColor[row][col] = null;
				else if(board[row][col] == 1)
					continue;
				else if(board[row][col] > 1)
					blockColor[row][col] = c;
			}
	}
	
	/**Retrieves the colors for each block of Tetris.
	 * @return the 2-D array of colors*/
	public Color[][] getColorArray() {
		return blockColor;
	}
	
	/**Moves the Tetris blocks down the screen.*/
	private void moveDown() {
		if(checkDown())
		{
			for(int row = BB - 1; row >= UB; row --)
				for(int col = LB; col <= RB && col < board[row].length && col >= 0; col ++)
				{
					if(board[row][col] > 1)
					{
						board[row + 1][col] = board[row][col];
						board[row][col] = 0;
						blockColor[row + 1][col] = blockColor[row][col];
						blockColor[row][col] = null;
					}
				}
			BB ++; UB ++;
		}
		else
		{
			setOnes();
			resetLimits();
		}
	}
	
	/**Checks to see if the current, movable block will run into another
	 * block or the edge of the board when moved down.
	 * @return false if being moved down would exceed the edge of the board
	 * or if the block would run into another Tetris piece; true otherwise*/
	private boolean checkDown()
	{
		for(int row = UB; row <= BB; row ++)
		{
			if(row < board.length && !checkRow(row) && row + 1 < board.length && checkRow(row))
				return true;
			else if(row < board.length && !checkRow(row) && row + 1 >= board.length)
				return false;
			for(int col = LB; col <= RB && col < board[0].length && col >= 0 
					&& row + 1 < board.length && row < board.length; col ++)
				if((board[row][col] > 1 && board[row + 1][col] == 1))
					return false;
		}
		return true;
	}
	
	/**Sets the value of the current block's spaces to 1, meaning that it is no longer moving.*/
	private void setOnes()
	{
		for(int row = UB; row <= BB && row < board.length; row ++)
			for(int col = LB; col <= RB; col ++)
			{
				if(board[row][col] != 0)
					board[row][col] = 1;
				else
					continue;
			}
	}
	
	/**Moves the block according to velX.*/
	private void move()
	{
		if(checkSurroundings() && velX != 0)
		{
			if(velX == -1)
				for(int row = BB - 1; row >= UB; row --)
					for(int col = LB; col <= RB; col ++)
					{
						if(col + velX < board[row].length && board[row][col] > 1)
						{
							board[row][col + velX] = board[row][col];
							board[row][col] = 0;
							blockColor[row][col + velX] = blockColor[row][col];
							blockColor[row][col] = null;
						}
					}
			else
				for(int row = UB; row <= BB && row < board.length; row ++)
					for(int col = RB; col >= LB; col --)
					{
						if(col + velX < board[row].length && board[row][col] > 1)
						{
							board[row][col + velX] = board[row][col];
							board[row][col] = 0;
							blockColor[row][col + velX] = blockColor[row][col];
							blockColor[row][col] = null;
						}
					}
			if(RB + velX < board[0].length && LB + velX >= 0)
			{	
				RB += velX; LB += velX;
			}
		}
	}
	
	/**Checks the current row for any segments of the current controlled block.
	 * @param row the row being checked
	 * @return false if there is a block segment being controlled; true if not*/
	private boolean checkRow(int row)
	{
		for(int index : board[row])
			if(index > 1)
				return false;
		return true;
	}
	
	/**Checks for filled in spaces near the current block.
	 * @return false if there is a non-zero number close by or if
	 * the block would exceed the limits of the 2-D integer array
	 * acting as the board; otherwise true*/
	private boolean checkSurroundings()
	{
		for(int row = UB; row <= BB && row < board.length; row ++)
			for(int col = LB; col <= RB ; col ++)
				if(checkBoundries(row, col) || checkFilling(row, col))
					return false;
		return true;
	}
	
	/**Checks to see if the next space moved into will exceed a boundary of the board.
	 * @param row the row on the board
	 * @param col the column on the board
	 * @return true if the block would go off the board; false if the block would be in bounds*/
	private boolean checkBoundries(int row, int col)
	{
		return ((col + velX >= board[row].length || col + velX < 0) && 
				(col < board[row].length && col >= 0 && board[row][col] > 1));
	}
	
	/**Checks to see if the next space moved into will hit a block.
	 * @param row the row on the board
	 * @param col the column on the board
	 * @return true if the spot is filled by a set block, or is equal 
	 * to 1; false if spot is a 0, or is empty*/
	private boolean checkFilling(int row, int col)
	{
		return (col + velX >= 0 && col + velX < board[row].length && 
				board[row][col] > 1 && board[row][col + velX] == 1);
	}
	
	/**Checks to see if the controlled block is clear to rotate.*/
	private boolean checkAround()
	{
		for(int rows = UB; rows <= BB && rows < board.length; rows ++)
			for(int cols = mid - 2; cols <= mid + 2; cols ++)
				if(cols < 0 || cols > board[0].length || board[rows][cols] == 1
				|| rows >= board.length)
					return false;
		return true;
	}
	
	/**Repositions the bounds in which the block is contained in.*/
	private void resetLimits()
	{
		LB = board[0].length / 2 - 2; RB = board[0].length / 2 + 2;
		UB = 0; BB = 4; blockPos = 0;
		board = blocks.blockGenerator(board, nextBlock);
		nextBlock = (int)(Math.random() * 7 + 2);
		assignColor(board[2][board[0].length / 2]);
	}
	
	/**Resets all necessary variables to make a new game.*/
	private void newGame()
	{
		if(points > greatestPoints)
			greatestPoints = points;
		blockColor = new Color[board.length][board[0].length];
		board = board(board.length, board[0].length);
		points = 0; resetLimits();
	}
	
	/**Checks to see if another block can be generated.
	 * @return true if a stationary block is within the block-generating
	 * space; false if the area is clear*/
	private boolean checkFill()
	{
		for(int rows = 0; rows <= 3; rows ++)
			for(int cols = board[0].length / 2 - 2; cols <= board[0].length / 2 + 2; cols ++)
			{
				if(board[rows][cols] == 0)
					continue;
				else if(board[rows][cols] == 1)
					return true;
			}
		return false;
	}
	
	/**Creates a new board. Sets all spots with the value of zero, indicating that no block is there.
	 * @param rows the amount of rows on the board
	 * @param columns the number of columns for the board
	 * @return a new board;*/
	private int[][] board(int rows, int columns)
	{
		int[][] b = new int[rows][columns];
		for(int row = rows - 1; row > 0; row --)
			for(int col = columns - 1; col >= 0; col --)
				b[row][col] = 0;
		return b;
	}
	
	public int[][] getBoard()
	{
		return board;
	}
	
	public int getNextBlock() {
		return nextBlock;
	}
	/**Clears the space between the bounds for the user-controlled block.*/
	private void clearArea()
	{
		for(int row = UB; row <= BB && row < board.length; row ++)
			for(int col = LB; col <= RB && col < board[row].length; col ++)
				if(board[row][col] > 1)
				{
					board[row][col] = 0;
					blockColor[row][col] = null;
				}
	}

	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == e.VK_LEFT || key == e.VK_A)
		{
			velX = -1;
		}
		else if(key == e.VK_RIGHT || key == e.VK_D)
		{
			velX = 1;
		}
		else if((key == e.VK_UP || key == e.VK_DOWN) && checkAround())
			rotate(e, LB, RB, UB, BB, board);
		else if(key == e.VK_SPACE)
			watch.setDelay(2);
		else if(key == e.VK_R)
			watch.setDelay((int)speed);
	}

	@SuppressWarnings("static-access")
	private void rotate(KeyEvent e, int leftLimit, int rightLimit, int upperLimit, int lowerLimit, int[][] board) {
		int num = 0;
		for(int row = upperLimit; row < lowerLimit; row ++)
			for(int col = leftLimit; col < rightLimit; col ++)
				if(board[row][col] > 0 && board[row][col] != 1)
				{
					num = board[row][col];
					break;
				}
		int key = e.getKeyCode();
		if(key == e.VK_UP)
		{
			if(blockPos < 3)
				blockPos ++;
			else
				blockPos = 0;
		}
		else if(key == e.VK_DOWN)
		{
			if(blockPos > 0)
				blockPos --;
			else
				blockPos = 3;
		}
		if(num == 8 || mid + 1 >= board.length || mid - 2 < 0)
			return;
		clearArea();
		if(num == 2)
			rotateLine();
		else if(num == 3)
			rotateRL();
		else if(num == 4)
			rotateL();
		else if(num == 5)
			rotateT();
		else if(num == 6)
			rotateZ();
		else if(num == 7)
			rotateS();
		coloring();
	}

	private void rotateS() {
		switch(blockPos)
		{
			case 1:
				board[BB - 3][mid - 1] = 7;
				board[BB - 2][mid - 1] = 7;
				board[BB - 2][mid] = 7;
				board[BB - 1][mid] = 7;
				break;
			case 3:
				board[BB - 3][mid - 1] = 7;
				board[BB - 2][mid - 1] = 7;
				board[BB - 2][mid] = 7;
				board[BB - 1][mid] = 7;
				break;
			default:
				board[BB - 2][mid + 1] = 7;
				board[BB - 2][mid] = 7;
				board[BB - 1][mid] = 7;
				board[BB - 1][mid - 1] = 7;
				break;
		}
	}
	private void rotateZ() {
		switch(blockPos)
		{
			case 1:
				board[BB - 3][mid + 1] = 6;
				board[BB - 2][mid + 1] = 6;
				board[BB - 2][mid] = 6;
				board[BB - 1][mid] = 6;
				break;
			case 3:
				board[BB - 3][mid + 1] = 6;
				board[BB - 2][mid + 1] = 6;
				board[BB - 2][mid] = 6;
				board[BB - 1][mid] = 6;
				break;
			default:
				board[BB - 2][mid - 1] = 6;
				board[BB - 2][mid] = 6;
				board[BB - 1][mid] = 6;
				board[BB - 1][mid + 1] = 6;
				break;
		}
	}
	private void rotateT() {
		switch(blockPos)
		{
			case 1:
				board[BB - 3][mid] = 5;
				board[BB - 2][mid + 1] = 5;
				board[BB - 2][mid] = 5;
				board[BB - 1][mid] = 5;
				break;
			case 2:
				board[BB - 3][mid] = 5;
				board[BB - 2][mid + 1] = 5;
				board[BB - 2][mid] = 5;
				board[BB - 2][mid - 1] = 5;
				break;
			case 3:
				board[BB - 3][mid] = 5;
				board[BB - 2][mid - 1] = 5;
				board[BB - 2][mid] = 5;
				board[BB - 1][mid] = 5;
				break;
			default:
				board[BB - 2][mid - 1] = 5;
				board[BB - 2][mid] = 5;
				board[BB - 1][mid] = 5;
				board[BB - 2][mid + 1] = 5;
				break;
		}
	}
	private void rotateL() {
		switch(blockPos)
		{
			default:
				board[BB - 3][mid] = 4;
				board[BB - 2][mid] = 4;
				board[BB - 1][mid] = 4;
				board[BB - 1][mid + 1] = 4;
				break;
			case 1:
				board[BB - 2][mid - 1] = 4;
				board[BB - 2][mid + 1] = 4;
				board[BB - 2][mid] = 4;
				board[BB - 3][mid + 1] = 4;
				break;
			case 2:
				board[BB - 3][mid] = 4;
				board[BB - 3][mid - 1] = 4;
				board[BB - 2][mid] = 4;
				board[BB - 1][mid] = 4;
				break;
			case 3:
				board[BB - 2][mid + 1] = 4;
				board[BB - 2][mid] = 4;
				board[BB - 2][mid - 1] = 4;
				board[BB - 1][mid - 1] = 4;
				break;
		}
	}
	private void rotateRL() {
		switch(blockPos)
		{
			default:
				board[BB - 3][mid] = 3;
				board[BB - 2][mid] = 3;
				board[BB - 1][mid] = 3;
				board[BB - 1][mid - 1] = 3;
				break;
			case 1:
				board[BB - 2][mid - 1] = 3;
				board[BB - 2][mid + 1] = 3;
				board[BB - 2][mid] = 3;
				board[BB - 1][mid + 1] = 3;
				break;
			case 2:
				board[BB - 3][mid] = 3;
				board[BB - 3][mid + 1] = 3;
				board[BB - 2][mid] = 3;
				board[BB - 1][mid] = 3;
				break;
			case 3:
				board[BB - 2][mid + 1] = 3;
				board[BB - 2][mid] = 3;
				board[BB - 2][mid - 1] = 3;
				board[BB - 3][mid - 1] = 3;
				break;
		}
	}
	private void rotateLine() {
		switch(blockPos)
		{
			case 1:
				board[BB - 2][mid] = 2;
				board[BB - 2][mid + 1] = 2;
				board[BB - 2][mid - 1] = 2;
				board[BB - 2][mid - 2] = 2;
				break;
			case 3:
				board[BB - 2][mid] = 2;
				board[BB - 2][mid - 1] = 2;
				board[BB - 2][mid - 2] = 2;
				board[BB - 2][mid + 1] = 2;
				break;
			default:
				board[BB - 4][mid] = 2;
				board[BB - 3][mid] = 2;
				board[BB - 2][mid] = 2;
				board[BB - 1][mid] = 2;
				break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		move();
		velX = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

}
