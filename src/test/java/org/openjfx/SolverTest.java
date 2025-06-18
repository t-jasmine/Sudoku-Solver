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


}
