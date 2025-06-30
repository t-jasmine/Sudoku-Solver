package org.openjfx.miniSudoku;

import java.util.Random;

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

    public void generateBoard(int numEmptyCells)
    {
        MiniSolver s = new MiniSolver();
        this.clear();
        Random rand = new Random();

        //Getting a random solution
        int randR = rand.nextInt(4);
        int randC = rand.nextInt(4);
        int randVal = 1 + rand.nextInt(4);
        this.set(randC, randR, randVal);

        MiniBoard solution = s.solve(this);
        if(solution!=null)
        {
            for(int r = 0; r<4; r++)
            {
                for(int c = 0; c<4; c++)
                {
                    this.set(c,r,solution.get(c,r));
                }
            }
        }
        
        //Removing cells from the solution to get the generated board
        for (int i = 0; i<numEmptyCells; i++) {
            int r = rand.nextInt(4);
            int c = rand.nextInt(4);

            
            if(this.get(c,r)!=null)
            {
                this.set(c,r,null);
            }
            else
            {
                i--;
            }
            
        }
        
    }

}
