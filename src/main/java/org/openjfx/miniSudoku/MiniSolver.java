package org.openjfx.miniSudoku;

import java.util.HashSet;
import java.util.Set;

import org.openjfx.Board;
import org.openjfx.Solver;

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

   /* 
    public boolean validRow(MiniBoard b, int r)
    {
        Set<Integer> n = new HashSet<>(checker);
        for(int c = 0; c<4; c++)
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

    public boolean validColumn(MiniBoard b, int c)
    {
        Set<Integer> n = new HashSet<>(checker);
        for(int r = 0; r<4; r++)
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
    */

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

    /*
    public boolean validAllRows(MiniBoard b)
    {
        for(int r = 0; r<4; r++)
        {
            if(!validRow(b,r))
            {
                return false;
            }
        }
        return true;
    }

    public boolean validAllColumns(MiniBoard b)
    {
        for(int c = 0; c<4; c++)
        {
            if(!validColumn(b,c))
            {
                return false;
            }
        }
        return true;
    }
    */

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

    /*
    public boolean validBoard(MiniBoard b)
    {
        return validAllRows(b) && validAllColumns(b) && validAllBoxes(b);
    }

    public boolean complete(MiniBoard b) //Checks if the board is completely filled
    {
        for(int r = 0; r<4; r++)
        {
            for(int c = 0; c<4; c++)
            {
                if(b.get(c,r) == null)
                {
                    return false;
                }
            }
        }
        return true;
    }
    

    public boolean completeAndValid(MiniBoard b)
    {
        return complete(b) && validBoard(b);
    }
    */

    /*
    MiniBoard solve(MiniBoard b)
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
    */
}
