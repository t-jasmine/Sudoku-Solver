package org.openjfx;

public class Board 
{
    protected final Integer[][] board;

    public Board() //Default 9x9 Grid
    {
        this(9);
    }

    public Board(int size)
    {
        board = new Integer[size][size];
    }

    public Board(Board b) //Makes a copy of Board b
    {
        int size = b.board.length;
        board = new Integer[size][size];
        for(int r = 0; r<size; r++)
        {
            for(int c = 0; c<size; c++)
            {
                board[c][r] = b.get(c,r);
            }
        }
    }

    public Integer length()
    {
        return board.length;
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
