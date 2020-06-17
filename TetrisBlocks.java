package Tetris;

public class TetrisBlocks {
	/**Creates an S-block by filling in the appropriate slots of an array.
	 * @param board the board for playing Tetris
	 * @param a modified version of the board with an S-block*/
	private int[][] sBlock(int[][] board)
	{
		board[2][board[0].length / 2 + 1] = 7;
		board[2][board[0].length / 2] = 7;
		board[3][board[0].length / 2] = 7;
		board[3][board[0].length / 2 - 1] = 7;
		return board;
	}
	
	private int[][] zBlock(int[][] board)
	{
		board[2][board[0].length / 2 - 1] = 6;
		board[2][board[0].length / 2] = 6;
		board[3][board[0].length / 2] = 6;
		board[3][board[0].length / 2 + 1] = 6;
		return board;
	}
	
	private int[][] tBlock(int[][] board)
	{
		board[2][board[0].length / 2 + 1] = 5;
		board[2][board[0].length / 2 - 1] = 5;
		board[2][board[0].length / 2] = 5;
		board[3][board[0].length / 2] = 5;
		return board;
	}
	
	private int[][] lBlock(int[][] board)
	{
		board[1][board[0].length / 2] = 4;
		board[2][board[0].length / 2] = 4;
		board[3][board[0].length / 2] = 4;
		board[3][board[0].length / 2 + 1] = 4;
		return board;
	}
	
	private int[][] reverseLBlock(int[][] board)
	{
		board[1][board[0].length / 2] = 3;
		board[2][board[0].length / 2] = 3;
		board[3][board[0].length / 2] = 3;
		board[3][board[0].length / 2 - 1] = 3;
		return board;
	}
	
	private int[][] boxBlock(int[][] board)
	{
		board[2][board[0].length / 2] = 8;
		board[2][board[0].length / 2 + 1] = 8;
		board[3][board[0].length / 2] = 8;
		board[3][board[0].length / 2 + 1] = 8;
		return board;
	}
	
	private int[][] lineBlock(int[][] board)
	{
		board[0][board[0].length / 2] = 2;
		board[1][board[0].length / 2] = 2;
		board[2][board[0].length / 2] = 2;
		board[3][board[0].length / 2] = 2;
		return board;
	}
	
	public int[][] blockGenerator(int[][] board, int num)
	{
		switch(num)
		{
			case 2:
				return zBlock(board);
			case 3:
				return tBlock(board);
			case 4:
				return lBlock(board);
			case 5:
				return reverseLBlock(board);
			case 6:
				return lineBlock(board);
			case 7:
				return boxBlock(board);
			default:
				return sBlock(board);
		}
	}
}
