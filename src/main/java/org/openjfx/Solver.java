package org.openjfx;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    private final Set<Integer> checker; // Set of numbers 1-9
    // This set is used to check if a number is already present in the row, column, or box.

    public Solver()
    {
        checker = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            checker.add(i);  
        }
    }

    public boolean validRow(Board b, int r)
    {
        Set<Integer> n = new HashSet<>(checker);
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
        Set<Integer> n = new HashSet<>(checker);
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

    public boolean validBox(Board b, int boxC, int boxR)
    {
        Set<Integer> n = new HashSet<>(checker);
        for(int r=0; r < 3; r++)
        {
            for(int c=0; c < 3; c++)
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

    public boolean validAllRows(Board b)
    {
        for(int r = 0; r<9; r++)
        {
            if(!validRow(b,r))
            {
                return false;
            }
        }
        return true;
    }

    public boolean validAllColumns(Board b)
    {
        for(int c = 0; c<9; c++)
        {
            if(!validColumn(b,c))
            {
                return false;
            }
        }
        return true;
    }

    public boolean validAllBoxes(Board b)
    {
        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c < 3; c++)
            {
                if(!validBox(b,c*3,r*3))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validBoard(Board b)
    {
        return validAllRows(b) && validAllColumns(b) && validAllBoxes(b);
    }

    public boolean complete(Board b) //Checks if the board is completely filled
    {
        for(int r = 0; r<9; r++)
        {
            for(int c = 0; c<9; c++)
            {
                if(b.get(c,r) == null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean completeAndValid(Board b)
    {
        return complete(b) && validBoard(b);
    }

    Board solve(Board b)
    {
        if (!validBoard(b))
        {
            return null;
        }
        if (completeAndValid(b))
        {
            return b;
        }

        Board copy = new Board(b);
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c<9; c++)
            {
                Integer i = copy.get(c,r);
                if(i == null)
                {
                    Set<Integer> n = new HashSet<>(checker);
                    //Trying potential numbers 1-9
                    for (Integer num : n)
                    {
                        copy.set(c,r,num);
                        Board solution = solve(copy);
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
