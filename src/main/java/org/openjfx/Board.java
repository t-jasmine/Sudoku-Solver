package org.openjfx;

public class Board 
{
    private final Integer[][] board = new Integer[9][9];

    public Board()
    {
        //Initializing the board with null values
    }

    public Board(Board b) //Makes a copy of Board b
    {
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                board[c][r] = b.get(c,r);
            }
        }
    }

    public Integer get(int x, int y)
    {
        return board[x][y];
    }

    public void set(int x, int y, Integer value)
    {
        board[x][y] = value;
    }

}
