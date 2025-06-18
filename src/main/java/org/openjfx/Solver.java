package org.openjfx;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    private Set<Integer> checker;

    public Solver()
    {
        checker = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            checker.add(i);  
        }
    }

    public boolean validRow(Board b, int r)
    {
        Set<Integer> n = new HashSet<Integer>(checker);
        for(int c = 0; c<9; c++)
        {
            Integer i = b.get(c,r);
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
        return true;
    }

    public boolean validColumn(Board b, int c)
    {
        Set<Integer> n = new HashSet<Integer>(checker);
        for(int r = 0; r<9; r++)
        {
            Integer i = b.get(c,r);
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
        return true;
    }

    public boolean validBox()
    {
        // This method is not implemented yet.
        // It should check if a 3x3 box in the Sudoku board is valid.
        return true; // Placeholder return value
    }

}
