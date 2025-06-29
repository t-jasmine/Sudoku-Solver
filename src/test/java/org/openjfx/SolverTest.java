package org.openjfx;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SolverTest {
    /*
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
    void testCompleteAndValid()
    {
        Solver s = new Solver();
        Board b = new Board();

        //Checking with June 23rd NYT Hard Sudoku Puzzle lol

        //First Row
        b.set(0,0,5);
        b.set(1,0,6);;
        b.set(2,0,2);
        b.set(3,0,1);
        b.set(4,0,7);
        b.set(5,0,4);
        b.set(6,0,8);
        b.set(7,0,9);
        b.set(8,0,3);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Second Row
        b.set(0, 1, 4);
        b.set(1, 1, 3);
        b.set(2, 1, 9);
        b.set(3, 1, 2);
        b.set(4, 1, 6);
        b.set(5, 1, 8);
        b.set(6, 1, 5);
        b.set(7, 1, 1);
        b.set(8, 1, 7);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Third Row
        b.set(0,2,8);
        b.set(1,2,7);
        b.set(2,2,1);
        b.set(3,2,5);
        b.set(4,2,9);
        b.set(5,2,3);
        b.set(6,2,2);
        b.set(7,2,6);
        b.set(8,2,4);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Fourth Row
        b.set(0,3,9);
        b.set(1,3,8);
        b.set(2,3,7);
        b.set(3,3,4);
        b.set(4,3,3);
        b.set(5,3,2);
        b.set(6,3,6);
        b.set(7,3,5);
        b.set(8,3,1);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Fifth Row
        b.set(0,4,2);
        b.set(1,4,1);
        b.set(2,4,5);
        b.set(3,4,6);
        b.set(4,4,8);
        b.set(5,4,7);
        b.set(6,4,3);
        b.set(7,4,4);
        b.set(8,4,9);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Sixth Row
        b.set(0,5,6);
        b.set(1,5,4);
        b.set(2,5,3);
        b.set(3,5,9);
        b.set(4,5,1);
        b.set(5,5,5);
        b.set(6,5,7);
        b.set(7,5,2);
        b.set(8,5,8);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Seventh Row
        b.set(0,6,3);
        b.set(1,6,2);
        b.set(2,6,6);
        b.set(3,6,8);
        b.set(4,6,4);
        b.set(5,6,9);
        b.set(6,6,1);
        b.set(7,6,7);
        b.set(8,6,5);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Eighth Row
        b.set(0,7,1);
        b.set(1,7,9);
        b.set(2,7,8);
        b.set(3,7,7);
        b.set(4,7,5);
        b.set(5,7,6);
        b.set(6,7,4);
        b.set(7,7,3);
        b.set(8,7,2);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        //Ninth Row
        b.set(0,8,7);
        b.set(1,8,5);
        b.set(2,8,4);
        b.set(3,8,3);
        b.set(4,8,2);
        b.set(5,8,1);
        b.set(6,8,9);
        b.set(7,8,8);
        //b.set(8,8,6);
        assertTrue(s.validBoard(b));
        assertFalse(s.completeAndValid(b));

        Board solution = s.solve(b);
        assertTrue(solution.get(8,8)==6);
        assertTrue(s.completeAndValid(solution));
    }
    */
}
