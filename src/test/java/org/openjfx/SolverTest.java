package org.openjfx;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SolverTest {

    @Test
    void testValidRow()
    {
        Solver s = new Solver();
        Board b = new Board();

        //Checking true for no duplicates
        b.set(0,0,9);
        assertTrue(s.validRow(b,0));

        //Checking false for duplicates
        b.set(0,1,9);
        b.set(1,1,9);
        assertFalse(s.validRow(b,1));

    }

    @Test
    void testValidColumn()
    {
        Solver s = new Solver();
        Board b = new Board();

        //Checking true for no duplicates
        b.set(0,0,9);
        assertTrue(s.validColumn(b,0));

        //Checking false for duplicates
        b.set(1,0,9);
        b.set(1,1,9);
        assertFalse(s.validColumn(b,1));
    }

    @Test
    void testValidBox()
    {
        Solver s = new Solver();
        Board b = new Board();

        //Checking true for no duplicates
        b.set(0,0,9);
        assertTrue(s.validBox(b,0,0));

        //Checking false for duplicates
        b.set(1,1,9);
        assertFalse(s.validBox(b,0,0));
    }

    @Test
    void testValidAllRows()
    {
        Solver s = new Solver();
        Board b = new Board();
        for(int i = 0; i<9; i++)
        {
            b.set(i,i,9);
        }
        assertTrue(s.validAllRows(b));

        b.set(7,8,9);
        assertFalse(s.validAllRows(b));
    }

    @Test
    void testValidAllColumns()
    {
        Solver s = new Solver();
        Board b = new Board();
        for(int i = 0; i<9; i++)
        {
            b.set(i,i,9);
        }
        assertTrue(s.validAllColumns(b));
        b.set(8,7,9);
        assertFalse(s.validAllColumns(b));
    }

    @Test
    void testValidBoxes()
    {
        Solver s = new Solver();
        Board b = new Board();
        for(int r = 0; r<3; r++)
        {
            for(int c = 0; c<3; c++)
            {
                b.set(c*3,r*3,9);
            }
        }
        assertTrue(s.validAllBoxes(b));

        b.set(8,8,9);
        assertFalse(s.validAllBoxes(b));
    }

    @Test
    void testValidBoard()
    {
        
    }

}
