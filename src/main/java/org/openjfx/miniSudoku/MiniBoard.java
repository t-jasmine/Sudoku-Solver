package org.openjfx.miniSudoku;

import org.openjfx.Board;

public class MiniBoard extends Board
{
    public MiniBoard()
    {
        super(4);
    }

    public MiniBoard(MiniBoard b) //Makes a copy of Board b
    {
        super(b);
    }
    
    public MiniBoard(Board b) //Makes a copy of Board b
    {
        super(b);
    }

}
