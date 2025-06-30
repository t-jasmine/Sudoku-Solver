package org.openjfx.miniSudoku;

import java.util.HashSet;
import java.util.Set;

import org.openjfx.Board;
import org.openjfx.Solver;

//This class is used to check if the mini board is correct
public class MiniSolver extends Solver {
    public MiniSolver()
    {
        super();
        checker.clear();
        for(int i = 1; i<=4; i++)
        {
            checker.add(i);
        }
    }

    @Override
    public boolean validBox(Board b, int boxC, int boxR)
    {
        Set<Integer> n = new HashSet<>(checker);
        for(int r=0; r < 2; r++)
        {
            for(int c=0; c < 2; c++)
            {
                Integer i = b.get(boxC + c,boxR + r);
                if(i!=null)
                {
                    if(!n.contains(i))
                    {
                        return false;
                    }
                    else
                    {
                        n.remove(i);
                    }
                }  
            }
        }
        return true;
    }

    @Override
    public boolean validAllBoxes(Board b)
    {
        for(int r = 0; r < 2; r++)
        {
            for(int c = 0; c < 2; c++)
            {
                if(!validBox(b,c*2,r*2))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public MiniBoard solve(MiniBoard b)
    {
        if (!validBoard(b))
        {
            return null;
        }
        if (completeAndValid(b))
        {
            return b;
        }

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c<4; c++)
            {
                Integer i = b.get(c,r);
                if(i == null)
                {
                    Set<Integer> n = new HashSet<>(checker);
                    //Trying potential numbers 1-4
                    for (Integer num : n)
                    {
                        MiniBoard copy = new MiniBoard(b);
                        copy.set(c,r,num);
                        MiniBoard solution = solve(copy);
                        if (solution!=null) {
                            return solution; // Return found solution
                        }
                    }
                    return null; // No solution found
                }
            }
        }
        return null;    
    }
    
}
