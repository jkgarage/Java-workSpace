package br.game.sudoku;

public class SudokuSolver {

	static int _board_size = 0;
	static byte [][] counter;
	static byte [][] zoneCounter;

	public static void main (String [] args)
	{
		byte [][] board = constructSudokuBoard();
		byte [] marker = new byte[10];

		/****************************************************************/
		boolean isSolved = false;
		byte tvalue = -1;
		byte countFilledRow = 0;
		
		printBoard(board);
		System.out.println("Thinking ...");
		
		while ( !isSolved )
		{
			tvalue = -1;
			countFilledRow = 0;
			
			//scan through to fill in any row/col/zone having only 1 cell left to be filled
			for (byte c = 0; c < _board_size; c++)
			{
				if ( counter[0][c] == 1 )
				{
					marker = new byte[10];
					byte posi = -1;
					
					//do the filling for column here
					for (byte ct = 0; ct < _board_size; ct++ )
					{
						tvalue = board[ct][c];
						if ( board[ct][c] != 0 )
							marker[tvalue] = 1;
						else
							posi = ct;
					}
					for (byte tmp = 1; tmp < 10; tmp++)
						if (marker[tmp] == 0)
						{
							fillCell (posi, c, tmp, board);
							break;
						}
					
				}//end of fill column
				else if (counter[0][c] == 0)
				{
					if (++countFilledRow == _board_size) 
						isSolved = true;
				}
				
				if ( counter[1][c] == 1 )
				{
					marker = new byte[10];
					byte posj = -1;
					
					//do the filling for row here
					for (byte ct = 0; ct < _board_size; ct++ )
					{
						tvalue = board[c][ct];
						
						if ( board[c][ct] != 0 )
							marker[tvalue] = 1;
						else
							posj = ct;
					}
					
					for (byte tmp = 1; tmp < 10; tmp++)
						if (marker[tmp] == 0)
						{
							fillCell (c, posj, tmp, board);
							break;
						}
				}//end of fill row
			}//end of scanning for row/col
			
			for (byte ci = 0; ci < zoneCounter.length; ci++)
				for (byte cj = 0; cj < zoneCounter.length; cj++)
					if (zoneCounter[ci][cj] == 1)
					{
						marker = new byte[10];
						int posi = -1, posj = -1;
						
						//find the missing number, and fill the missing cell
						for (int bi = ci*3; bi < ci*3+3; bi++) //bi = board index i
							for ( int bj = cj*3; bj < cj*3+3; bj++)
							{
								tvalue = board[bi][bj];
								if ( board[bi][bj] != 0 )
									marker[tvalue] = 1;
								else
								{
									posi = bi; posj = bj;
								}
							}

						for (byte tmp = 1; tmp < 10; tmp++)
							if (marker[tmp] == 0)
							{
								fillCell ( (byte)posi, (byte)posj, tmp, board);
								break;
							}
					}
		}//big while
		
		
		System.out.println ("After solved !!!!!");
		printBoard(board);
	}


	//initialize a sudoku board
	static byte [][] constructSudokuBoard ()
	{
		/*
		byte [][] result = { {0, 0, 0, 0, 0, 8, 0, 0, 4},
				{0, 8, 4, 0, 1, 6, 0, 0, 0},
				{0, 0, 0, 5, 0, 0, 1, 0, 0},
				{1, 0, 3, 8, 0, 0, 9, 0, 0},
				{6, 0, 8, 0, 0, 0, 4, 0, 3},
				{0, 0, 2, 0, 0, 9, 5, 0, 1},
				{0, 0, 7, 0, 0, 2, 0, 0, 0},
				{0, 0, 0, 7, 8, 0, 2, 6, 0},
				{2, 0, 0, 3, 0, 0, 0, 0, 0}  };  */
		
		byte [][] result = { {2,6,4,5,7,3,1,8,9},
				{8,1,7,2,4,9,3,6,5},
				{9,3,0,1,6,8,2,7,4},
				{1,8,6,4,3,0,0,2,7},
				{4,7,2,0,9,1,6,5,3},
				{3,0,9,6,2,7,0,4,1},
				{5,9,3,7,8,2,4,0,6},
				{7,0,8,3,0,6,5,9,2},
				{6,2,1,9,5,4,7,3,8}  };

		//initialize counters :
		//  row indicates the number of unfilled cells for each column
		//  column indicates the number of unfilled cells for each row
		_board_size = result[0].length;
		counter = new byte [2][_board_size];
		zoneCounter = new byte[_board_size/3][_board_size/3];

		for ( int i = 0; i < _board_size; i++ )
		{
			for ( int j = 0; j < _board_size; j++ )
			{
				if ( result[i][j] == 0 )
				{
					counter[0][j] ++;  //count for cells per column
					counter[1][i] ++;  //count for cells per row
					zoneCounter[i/3][j/3] ++;
				}
			}
		}

		return result;
	}
	
	static boolean fillCell (byte i, byte j, byte value, byte[][] board)
	{
		if (board[i][j] != 0 || value == 0) return false;
		
		board[i][j] = value;
		counter[1][i] --;
		counter[0][j] --;
		zoneCounter[i/3][j/3] --;
		
		return true;
	}
	
	
	static void printBoard (byte [][] board)
	{
		for (int i = 0; i < board.length; i++)
		{
			for ( int j = 0; j < board[0].length; j++)
				System.out.print( board[i][j] + " ");
			System.out.println();
		}
	}
}
